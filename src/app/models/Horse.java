package app.models;

import app.interfaces.Copyable;

public class Horse implements Copyable {
    private Coordinate coordinate;

    public Horse(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Horse getCopy() {
        return new Horse(this.coordinate.getCopy());
    }
}
