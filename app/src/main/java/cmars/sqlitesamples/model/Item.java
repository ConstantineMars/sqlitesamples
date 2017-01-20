package cmars.sqlitesamples.model;

import android.content.ContentValues;

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

    public Item(long id, long boxId, String name, int price, boolean isNew) {
        this.id = id;
        this.boxId = boxId;
        this.name = name;
        this.price = price;
        this.isNew = isNew;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Item._ID, id);
        values.put(StoreContract.Item.COLUMN_BOX_ID, boxId);
        values.put(StoreContract.Item.COLUMN_NAME, name);
        values.put(StoreContract.Item.COLUMN_PRICE, price);

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
