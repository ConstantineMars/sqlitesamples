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
            insertBox(db, box);
        }

        for(Item item:DefaultValues.Before.items) {
            insertItem(db, item);
        }
    }

    private void clearDb() {
        db.execSQL(StoreContract.Box.SQL_DELETE);
        db.execSQL(StoreContract.Box.SQL_CREATE);

        db.execSQL(StoreContract.Item.SQL_DELETE);
        db.execSQL(StoreContract.Item.SQL_CREATE);
    }

    private long insertBox(SQLiteDatabase db, Box box) {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Box.COLUMN_TITLE, box.getTitle());

        return db.insert(StoreContract.Box.TABLE_NAME, null, values);
    }

    private long insertItem(SQLiteDatabase db, Item item) {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Item.COLUMN_BOX_ID, item.getBoxId());
        values.put(StoreContract.Item.COLUMN_NAME, item.getName());
        values.put(StoreContract.Item.COLUMN_PRICE, item.getPrice());

        return db.insert(StoreContract.Item.TABLE_NAME, null, values);
    }

    public List<Box> queryBoxes() {
        String[] projection = {
                StoreContract.Box._ID,
                StoreContract.Box.COLUMN_TITLE
        };

        Cursor cursor = db.query(
                StoreContract.Box.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<Box> boxes = new ArrayList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Box._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(StoreContract.Box.COLUMN_TITLE));

            boxes.add(new Box(id, title));
        }
        cursor.close();

        return boxes;
    }

    public List<Item> queryItems(long boxId) {
        String selection = StoreContract.Item.COLUMN_BOX_ID + " = ?";
        String[] selectionArgs = { String.valueOf(boxId) };

        return queryItems(selection, selectionArgs);
    }

    public List<Item> queryAllItems(){
        return queryItems(null, null);
    }

    private List<Item> queryItems(String selection, String[] selectionArgs) {
        String[] projection = {
                StoreContract.Item._ID,
                StoreContract.Item.COLUMN_NAME,
                StoreContract.Item.COLUMN_PRICE
        };

        Cursor cursor = db.query(
                StoreContract.Item.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<Item> items = new ArrayList<>();
        while(cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Item._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_NAME));
            long boxId = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_BOX_ID));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_PRICE));

            items.add(new Item(id, boxId, name, price));
        }
        cursor.close();

        return items;
    }

    public void updateItems(Item[] newItemsArray) {
        List<Item> newItems = Arrays.asList(newItemsArray);
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
                insertItem(db, newItem);
            }
        }
    }

    void deleteItem(Item item) {

        String whereClause = StoreContract.Item._ID + " = ?";
        String[] whereArgs = { String.valueOf(item.getId()) };

        db.delete(StoreContract.Item.TABLE_NAME,
                whereClause,
                whereArgs);
    }

    int updateItem(Item item) {

        ContentValues values = item.toContentValues();

        String whereClause = StoreContract.Item._ID + " = ?";
        String[] whereArgs = { String.valueOf(item.getId()) };

        int result = db.update(StoreContract.Item.TABLE_NAME,
                values,
                whereClause,
                whereArgs);

        return result;
    }

    public void updateBoxes(Box[] newBoxes) {

    }
}
