package cmars.sqlitesamples.model;

/**
 * Created by Constantine Mars on 1/20/17.
 */

public class DefaultValues {

    public static Box BOX_A = new Box(1, "A");
    public static Box BOX_A_UPDATED = new Box(1, "A updated");

    public static Item ITEM_1 = new Item(1, 1, "a", 10);

    public static Item ITEM_2 = new Item(2, 1, "b", 20);
    public static Item ITEM_2_UPDATED = new Item(2, 1, "e", 50);

    public static Item ITEM_3 = new Item(3, 1, "c", 10);
    public static Item ITEM_5 = new Item(5, 1, "f", 10);

    public static Box BOX_B = new Box(2, "B");
    public static Item ITEM_4 = new Item(4, 2, "d", 10);

    public static Box BOX_C = new Box(3, "C");
    public static Item ITEM_6 = new Item(6, 3, "g", 10, true);

    public static Box BOX_D = new Box(4, "D");

    public static class Before {
        public static Box[] boxes = {
                BOX_A,
                BOX_B,
                BOX_C
        };

        public static Item[] items = {
                ITEM_1,
                ITEM_2,
                ITEM_3,
                ITEM_4,
                ITEM_6
        };
    }

    public static class New {
        public static Box[] boxes = {
                BOX_A,
                BOX_C,
                BOX_D
        };
        public static Item[] items = {
                ITEM_1,
                ITEM_2_UPDATED,
                ITEM_5
        };
    }
}
