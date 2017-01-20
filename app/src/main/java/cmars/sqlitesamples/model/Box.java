package cmars.sqlitesamples.model;

import android.content.ContentValues;

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

    ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(StoreContract.Box._ID, id);
        values.put(StoreContract.Box.COLUMN_TITLE, title);

        return values;
    }
}
