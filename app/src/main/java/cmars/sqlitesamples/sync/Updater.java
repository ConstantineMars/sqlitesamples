package cmars.sqlitesamples.sync;

import java.util.List;

import lombok.Data;

/**
 * Created by Constantine Mars on 1/20/17.
 */

@Data
public class Updater<T> {

    public void update(Store<T> store) {
        List<T> items = store.queryAll();
        List<T> newItems = store.getAllNew();

//        First pass - find which old items to delete and which to update
        for(T item:items) {
            boolean found = false;

            for(T newItem:newItems) {
                if(newItem.equals(item)) {
                    store.update(newItem);
                    found = true;
                }
            }

            if(!found && store.eligibleToDelete(item)) {
                store.delete(item);
            }
        }

//        Second pass - find which items to insert
        for(T newItem:newItems) {
            boolean found = false;

            for(T item:items) {
                if(newItem.equals(item)) {
                    found = true;
                }
            }

            if(!found) {
                store.insert(newItem);
            }
        }
    }
}
