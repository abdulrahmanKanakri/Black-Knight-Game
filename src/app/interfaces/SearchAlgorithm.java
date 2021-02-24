package app.interfaces;

import app.models.Coordinate;
import app.models.State;

public interface SearchAlgorithm {
    void applyAlgorithm(boolean[][] visited, State source, Coordinate goal);
}
