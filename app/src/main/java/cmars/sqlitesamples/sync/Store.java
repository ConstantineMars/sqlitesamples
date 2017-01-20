package cmars.sqlitesamples.sync;

import java.util.List;

/**
 * Created by Constantine Mars on 1/20/17.
 */

public interface Store<T> {
    List<T> queryAll();
    List<T> getAllNew();
    void update(T t);
    void delete(T t);
    void insert(T t);
    boolean eligibleToDelete(T t);
}
