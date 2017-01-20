package cmars.sqlitesamples.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Constantine Mars on 1/19/17.
 */

@Data @AllArgsConstructor(suppressConstructorProperties = true)
public class Box {
    private long id;
    private String title;
}
