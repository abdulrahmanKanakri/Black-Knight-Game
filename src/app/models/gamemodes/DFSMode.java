package app.models.gamemodes;

import app.config.Config;
import app.controllers.GameController;
import app.interfaces.SearchAlgorithm;
import app.models.*;

import java.util.Collections;
import java.util.List;

public class DFSMode extends GameMode implements SearchAlgorithm {
    public DFSMode(GameController gameController) {
        super("DFS Mode", gameController);
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
    }

    @Override
    public void applyAlgorithm(boolean[][] visited, State source, Coordinate goal) {
        if(this.DFSRecursive(visited, source, goal)) {
            this.gameController.addState(source);
            Collections.reverse(this.gameController.getGame().getStates());
            this.gameController.printStatesPath();
        } else {
            System.out.println("Error in DFS can't reach the goal");
        }
    }

    private boolean DFSRecursive(boolean[][] visited, State source, Coordinate goal) {
        int x = source.getHorse().getCoordinate().getX();
        int y = source.getHorse().getCoordinate().getY();
        visited[x][y] = true;
        if(goal.equals(source.getHorse().getCoordinate())) {
            System.out.println("Finished");
            return true;
        }
        List<State> availableStates = this.gameController.getAvailableNextStates(source);
        for(State nextState : availableStates) {
            int xs = nextState.getHorse().getCoordinate().getX();
            int ys = nextState.getHorse().getCoordinate().getY();

            if(!visited[xs][ys]) {
                boolean res = DFSRecursive(visited, nextState, goal);
                if(res) {
                    this.gameController.addState(nextState);
                    return true;
                }
            }
        }
        return false;
    }

}
