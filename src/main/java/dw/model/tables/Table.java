package dw.model.tables;

import java.util.List;

public interface Table <C extends Column> {
    int getId();
    String getName();
    List<C> getColumns();
}
