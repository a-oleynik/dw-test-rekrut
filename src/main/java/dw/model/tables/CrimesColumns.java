package dw.model.tables;

public enum CrimesColumns implements Column {
    LATITUDE("Latitude", 9102),
    SERIAL_NUMBER("Serial number", 9104),
    ID("ID", 9106);

    private final String title;
    private final int id;


    CrimesColumns(String title, int id) {
        this.title = title;
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return id;
    }
}
