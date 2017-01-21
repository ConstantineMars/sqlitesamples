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
public class Box {
    private long id;
    private String title;

    public Box(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(StoreContract.Box._ID));
        title = cursor.getString(cursor.getColumnIndexOrThrow(StoreContract.Box.COLUMN_TITLE));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Box._ID, id);
        values.put(StoreContract.Box.COLUMN_TITLE, title);

        return values;
    }
}
