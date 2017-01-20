package cmars.sqlitesamples.sync;

import java.util.ArrayList;
import java.util.List;

import cmars.sqlitesamples.db.StoreHelper;
import cmars.sqlitesamples.model.Box;
import cmars.sqlitesamples.model.Item;
import lombok.Data;

/**
 * Created by Constantine Mars on 1/20/17.
 */

@Data
public abstract class Updater {
    List<Box> newBoxes = new ArrayList<>();
    List<Item> newItems = new ArrayList<>();

    public void update(StoreHelper storeHelper, Box[]newBoxes, Item[] newItems) {
//        storeHelper
    }
}
