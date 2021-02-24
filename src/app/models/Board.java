package app.models;

import app.interfaces.Copyable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Copyable {
    private int n;
    private int m;
    private List<Cell> cells;
    private final Coordinate exitHole;

    public Board(int n, int m, List<Cell> cells, Coordinate exitHole) {
        this.n = n;
        this.m = m;
        this.cells = cells;
        this.exitHole = exitHole;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Coordinate getExitHole() {
        return exitHole;
    }

    @Override
    public Board getCopy() {
        List<Cell> cellList = new ArrayList<>();
        for(Cell cell : cells) {
            cellList.add(cell.getCopy());
        }
        return new Board(this.n, this.m, cellList, new Coordinate(this.exitHole.getX(), this.exitHole.getY()));
    }
}
