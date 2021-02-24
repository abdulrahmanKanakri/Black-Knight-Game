package utils;

import app.enums.CellType;
import app.models.Cell;
import app.models.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Cell> initCells(int n, int m) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < n * m; i++) {
            int x = (i % n) + 1;
            int y = (i / n) + 1;
            cells.add(new Cell(CellType.TILE, new Coordinate(x, y)));
        }
        return cells;
    }
}
