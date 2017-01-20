package cmars.sqlitesamples.model;

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

    public Item(long boxId, String name, int price) {
        this.boxId = boxId;
        this.name = name;
        this.price = price;
    }
}
