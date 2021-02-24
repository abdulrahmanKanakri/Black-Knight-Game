package app.models;

import app.enums.CellType;
import app.interfaces.Copyable;

public class Cell implements Copyable {
    private CellType type;
    private Coordinate coordinate;

    public Cell(CellType type, Coordinate coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Cell getCopy() {
        return new Cell(this.type, this.coordinate.getCopy());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "type=" + type +
                ", coordinate=" + coordinate +
                '}';
    }
}
