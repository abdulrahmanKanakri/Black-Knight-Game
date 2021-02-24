package app.models;

import app.interfaces.Copyable;

public class Coordinate implements Copyable {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Coordinate getCopy() {
        return new Coordinate(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinate) {
            return ((Coordinate) obj).getX() == this.x && ((Coordinate) obj).getY() == this.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
