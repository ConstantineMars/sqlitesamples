package cmars.sqlitesamples.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cmars.sqlitesamples.model.Box;
import cmars.sqlitesamples.model.DefaultValues;
import cmars.sqlitesamples.model.Item;
import cmars.sqlitesamples.sync.Store;
import cmars.sqlitesamples.sync.Updater;

/**
 * Created by Constantine Mars on 1/19/17.
 */

public class StoreHelper {
    private StoreDbHelper dbHelper;
    private SQLiteDatabase db;

    public StoreHelper(Context context) {
        dbHelper = new StoreDbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void initDefaultValues() {

        clearDb();

        for(Box box:DefaultValues.Before.boxes) {
            insertBox(box);
        }

        for(Item item:DefaultValues.Before.items) {
            insertItem(item);
        }
    }

    private void clearDb() {
        db.execSQL(StoreContract.Box.SQL_DELETE);
        db.execSQL(StoreContract.Box.SQL_CREATE);

        db.execSQL(StoreContract.Item.SQL_DELETE);
        db.execSQL(StoreContract.Item.SQL_CREATE);
    }

    private long insertBox(Box box) {
        return insert(box.toContentValues(),
                StoreContract.Box.TABLE_NAME);
    }

    private long insertItem(Item item) {
        return insert(item.toContentValues(),
                StoreContract.Item.TABLE_NAME);
    }

    private long insert(ContentValues contentValues,
                        String tableName) {

        return db.insert(tableName,
                null,
                contentValues);
    }

    public List<Box> queryAllBoxes() {

        final List<Box> boxes = new ArrayList<>();
        Processor processor = new Processor() {
            @Override
            public void process(Cursor cursor) {
                boxes.add(new Box(cursor));
            }
        };

        query(StoreContract.Box.TABLE_NAME,                     // The table to query
                StoreContract.Box.PROJECTION,
                null,
                null,
                processor);

        return boxes;
    }

    public List<Item> queryItems(long boxId) {
        String selection = StoreContract.Item.COLUMN_BOX_ID + " = ?";
        String[] selectionArgs = { String.valueOf(boxId) };

        return queryItems(selection, selectionArgs);
    }

    public List<Item> queryNewItems(long boxId) {
        String selection = StoreContract.Item.COLUMN_BOX_ID + " = ?" +
                " AND " +
                StoreContract.Item.COLUMN_IS_NEW + " = ?";

        String[] selectionArgs = {String.valueOf(boxId),
                String.valueOf(1)};

        return queryItems(selection, selectionArgs);
    }

    public List<Item> queryAllItems(){
        return queryItems(null, null);
    }

    private List<Item> queryItems(String selection, String[] selectionArgs) {

        final List<Item> items = new ArrayList<>();
        Processor processor = new Processor() {
            @Override
            public void process(Cursor cursor) {
                items.add(new Item(cursor));
            }
        };

        query(StoreContract.Item.TABLE_NAME,                     // The table to query
                StoreContract.Item.PROJECTION,
                selection,
                selectionArgs,
                processor);

        return items;
    }

    private void query(String tableName,
                       String[] projection,
                       String selection,
                       String[] selectionArgs,
                       Processor processor) {
        Cursor cursor = db.query(
                tableName,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        while(cursor.moveToNext()) {
            processor.process(cursor);
        }
        cursor.close();
    }

    public void updateItemsWithUpdater(final Item[] newItems) {
        Updater<Item> itemUpdater = new Updater<>();
        Store<Item> itemStore = new Store<Item>() {
            @Override
            public List<Item> queryAll() {
                return queryAllItems();
            }

            @Override
            public List<Item> getAllNew() {
                return Arrays.asList(newItems);
            }

            @Override
            public void update(Item item) {
                updateItem(item);
            }

            @Override
            public void delete(Item item) {
                deleteItem(item);
            }

            @Override
            public void insert(Item item) {
                insertItem(item);
            }

            @Override
            public boolean eligibleToDelete(Item item) {
                return !item.isNew();
            }
        };

        itemUpdater.update(itemStore);
    }

    public void updateBoxesWithUpdater(final Box[] newBoxes) {
        Updater<Box> itemUpdater = new Updater<>();
        Store<Box> itemStore = new Store<Box>() {
            @Override
            public List<Box> queryAll() {
                return queryAllBoxes();
            }

            @Override
            public List<Box> getAllNew() {
                return Arrays.asList(newBoxes);
            }

            @Override
            public void update(Box box) {
                updateBox(box);
            }

            @Override
            public void delete(Box box) {
                deleteBox(box);
            }

            @Override
            public void insert(Box box) {
                insertBox(box);
            }

            @Override
            public boolean eligibleToDelete(Box box) {
                return !hasDependencies(box);
            }
        };

        itemUpdater.update(itemStore);
    }

    private boolean hasDependencies(Box box) {
        List<Item> items = queryNewItems(box.getId());
        return items.size() > 0;
    }

    public void updateItems(Item[] newItems) {
        List<Item> items = queryAllItems();

//        First pass - find which old items to delete and which to update
        for(Item item:items) {
            boolean found = false;

            for(Item newItem:newItems) {
                if(newItem.getId() == item.getId()) {
                    updateItem(newItem);
                    found = true;
                }
            }

            if(!found && !item.isNew()) {
                deleteItem(item);
            }
        }

//        Second pass - find which items to insert
        for(Item newItem:newItems) {
            boolean found = false;

            for(Item item:items) {
                if(newItem.getId() == item.getId()) {
                    found = true;
                }
            }

            if(!found) {
                insertItem(newItem);
            }
        }
    }

    private int updateItem(Item item) {

        return update(item.toContentValues(),
                StoreContract.Item.TABLE_NAME,
                StoreContract.Item._ID,
                item.getId());
    }

    private int updateBox(Box box) {

        return update(box.toContentValues(),
                StoreContract.Box.TABLE_NAME,
                StoreContract.Box._ID,
                box.getId());
    }

    private int update(ContentValues contentValues,
                       String tableName,
                       String idColumn,
                       long id) {

        String whereClause = idColumn + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        int result = db.update(tableName,
                contentValues,
                whereClause,
                whereArgs);

        return result;
    }

    private void deleteBox(Box box) {
        delete(StoreContract.Box.TABLE_NAME,
                StoreContract.Box._ID,
                box.getId());
    }

    private void deleteItem(Item item) {
        delete(StoreContract.Item.TABLE_NAME,
                StoreContract.Item._ID,
                item.getId());
    }

    private void delete(String tableName,
                        String idColumn,
                        long id) {
        String whereClause = idColumn + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        db.delete(tableName,
                whereClause,
                whereArgs);
    }
}
