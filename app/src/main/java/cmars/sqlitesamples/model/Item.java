package cmars.sqlitesamples.model;

import android.content.ContentValues;
import android.database.Cursor;

import cmars.sqlitesamples.db.StoreContract;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Constantine Mars on 1/19/17.
 */

@Data @AllArgsConstructor(suppressConstructorProperties = true)
public class Item {
    private long id;
    private long boxId;
    private String name;
    private int price;
    private boolean isNew;

    public Item(long id, long boxId, String name, int price) {
        this(id, boxId, name, price, false);
    }

    public Item(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Item._ID));
        boxId = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_BOX_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_NAME));
        price = cursor.getInt(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_PRICE));
        isNew = cursor.getInt(cursor.getColumnIndexOrThrow(StoreContract.Item.COLUMN_IS_NEW)) == 1;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Item._ID, id);
        values.put(StoreContract.Item.COLUMN_BOX_ID, boxId);
        values.put(StoreContract.Item.COLUMN_NAME, name);
        values.put(StoreContract.Item.COLUMN_PRICE, price);
        values.put(StoreContract.Item.COLUMN_IS_NEW, isNew);

        return values;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Item)) {
            return false;
        }
        if(o == this) {
            return true;
        }
        Item other = (Item) o;
        return other.getId() == this.getId();
    }
}
