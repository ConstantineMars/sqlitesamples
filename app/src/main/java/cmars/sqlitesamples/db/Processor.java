package cmars.sqlitesamples.db;

import android.database.Cursor;

/**
 * Created by Constantine Mars on 1/21/17.
 */

public interface Processor {
    void process(Cursor cursor);
}
