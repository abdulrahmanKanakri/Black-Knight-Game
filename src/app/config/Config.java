package app.config;

import app.enums.CellType;
import app.models.Cell;
import app.models.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private List<Cell> cells;
    private final Coordinate horseCoordinate;
    private final Coordinate exitHoleCoordinate;
    private final int n;
    private final int m;

    public Config(List<Cell> cells, Coordinate horseCoordinate, Coordinate exitHoleCoordinate, int n, int m) {
        this.cells = cells;
        this.horseCoordinate = horseCoordinate;
        this.exitHoleCoordinate = exitHoleCoordinate;
        this.n = n;
        this.m = m;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Coordinate getHorseCoordinate() {
        return horseCoordinate;
    }

    public Coordinate getExitHoleCoordinate() {
        return exitHoleCoordinate;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public static Config getDefaultConfig() {
        return new Config(initCells(), new Coordinate(1, 5), new Coordinate(4, 2), 6, 6);
    }

    private static List<Cell> initCells() {
        int n = 6, m = 6;
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < n * m; i++) {
            int x = (i % n) + 1;
            int y = (i / n) + 1;
            if (x == 4 && y == 2) {
                cells.add(new Cell(CellType.EXIT_HOLE, new Coordinate(x, y)));
            } else if(
                (x <= 3 && y <= 3) ||
                (x == 4 && y == 4) ||
                (x == 5 && y == 5)
            ) {
                cells.add(new Cell(CellType.EMPTY, new Coordinate(x, y)));
            } else {
                cells.add(new Cell(CellType.TILE, new Coordinate(x, y)));
            }
        }
        return cells;
    }

}
