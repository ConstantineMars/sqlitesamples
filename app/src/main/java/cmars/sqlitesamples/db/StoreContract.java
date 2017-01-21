package cmars.sqlitesamples.db;

import android.provider.BaseColumns;

/**
 * Created by Constantine Mars on 1/19/17.
 */

public final class StoreContract {
    private StoreContract() {}

    public static class Box implements BaseColumns {
        public static final String TABLE_NAME = "box";

        public static final String COLUMN_TITLE = "title";

        public static final String[] PROJECTION = {
                _ID,
                COLUMN_TITLE
        };

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT)";

        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_CLEAR =
                "DELETE FROM TABLE " + TABLE_NAME;
    }

    public static class Item implements BaseColumns {
        public static final String TABLE_NAME = "item";

        public static final String COLUMN_BOX_ID = "box_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IS_NEW = "is_new";

        public static final String[] PROJECTION = {
                _ID,
                COLUMN_BOX_ID,
                COLUMN_NAME,
                COLUMN_PRICE,
                COLUMN_IS_NEW
        };

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_BOX_ID + " INTEGER, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PRICE + " INTEGER, " +
                        COLUMN_IS_NEW + " INTEGER)";

        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_CLEAR =
                "DELETE FROM TABLE " + TABLE_NAME;
    }
}
