package cmars.sqlitesamples.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cmars.sqlitesamples.model.Box;
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

        long boxIdA = insertBox(db, "Devices");
        long boxIdB = insertBox(db, "Software");

        insertItem(db, boxIdA, "nexus 5x", 300);
        insertItem(db, boxIdA, "lg watch urbane", 900);
        insertItem(db, boxIdA, "iphone 5s", 145);

        insertItem(db, boxIdB, "Versions", 60);
    }

    private void clearDb() {
        db.execSQL(StoreContract.Box.SQL_DELETE);
        db.execSQL(StoreContract.Box.SQL_CREATE);

        db.execSQL(StoreContract.Item.SQL_DELETE);
        db.execSQL(StoreContract.Item.SQL_CREATE);
    }

    private long insertBox(SQLiteDatabase db, String title) {
        ContentValues values = new ContentValues();
        values.put(StoreContract.Box.COLUMN_TITLE, title);
        return db.insert(StoreContract.Box.TABLE_NAME, null, values);
    }

    private long insertItem(SQLiteDatabase db, long boxId, String name, int price) {
        ContentValues values = new ContentValues();
        values.put(StoreContract.Item.COLUMN_BOX_ID, boxId);
        values.put(StoreContract.Item.COLUMN_NAME, name);
        values.put(StoreContract.Item.COLUMN_PRICE, price);
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
        String[] projection = {
                StoreContract.Item._ID,
                StoreContract.Item.COLUMN_NAME,
                StoreContract.Item.COLUMN_PRICE
        };

        String selection = StoreContract.Item.COLUMN_BOX_ID + " = ?";
        String[] selectionArgs = { String.valueOf(boxId) };

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
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_PRICE));
            items.add(new Item(id, boxId, name, price));
        }
        cursor.close();

        return items;
    }

}
