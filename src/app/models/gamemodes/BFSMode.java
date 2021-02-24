package app.models.gamemodes;

import app.config.Config;
import app.controllers.GameController;
import app.interfaces.SearchAlgorithm;
import app.models.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSMode extends GameMode implements SearchAlgorithm {
    public BFSMode(GameController gameController) {
        super("BFS Mode", gameController);
    }

    @Override
    public void run() {
        Config config = this.iConfigRepo.getConfigData();
        int n = config.getN();
        int m = config.getM();
        int horseX = config.getHorseCoordinate().getX();
        int horseY = config.getHorseCoordinate().getY();
        List<Cell> cells = config.getCells();
        Board board = new Board(n, m, cells, config.getExitHoleCoordinate());
        Horse horse = new Horse(new Coordinate(horseX, horseY));
        State state = new State(board, horse);
        Coordinate goal = config.getExitHoleCoordinate();

        this.applyAlgorithm(new boolean[n + 1][m + 1], state, goal);
        this.gameController.printStatesPath();
    }

    @Override
    public void applyAlgorithm(boolean[][] visited, State source, Coordinate goal) {
        Queue<State> queue = new LinkedList<>();
        queue.add(source);
        source.addToPath(source);
        int x = source.getHorse().getCoordinate().getX();
        int y = source.getHorse().getCoordinate().getY();
        visited[x][y] = true;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if(goal.equals(state.getHorse().getCoordinate())) {
                for (State state1 : state.getPath()) {
                    this.gameController.addState(state1);
                }
                break;
            }
            List<State> availableStates = this.gameController.getAvailableNextStates(state);
            for(State nextState : availableStates) {
                int xs = nextState.getHorse().getCoordinate().getX();
                int ys = nextState.getHorse().getCoordinate().getY();
                if(!visited[xs][ys]) {
                    queue.add(nextState);
                    visited[xs][ys] = true;
                    for(State state1 : state.getPath()) {
                        nextState.addToPath(state1);
                    }
                    nextState.addToPath(nextState);
                }
            }
        }
    }
}
