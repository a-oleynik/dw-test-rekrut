package dw.model.tables;

import java.util.List;

public enum Tables implements Table<Column> {
    CRIMES(100, "users", CrimesColumns.values());

    private final int id;
    private final String name;
    private final List<Column> columns;

    Tables(int id, String name, Column[] columns) {
        this.id = id;
        this.name = name;
        this.columns = List.of(columns);
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public List<Column> getColumns() { return columns; }
}
