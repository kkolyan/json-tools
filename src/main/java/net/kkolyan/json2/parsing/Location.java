package net.kkolyan.json2.parsing;

/**
 * @author n.plekhanov
 */
public class Location {
    private int line;
    private int column;
    private String file;

    public Location(String file, int line, int column) {
        this.file = file;
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", file, line, column);
    }
}
