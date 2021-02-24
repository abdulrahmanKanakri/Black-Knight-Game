package app.models.gamemodes;

import app.config.Config;
import app.controllers.GameController;
import app.interfaces.SearchAlgorithm;
import app.models.*;

import java.util.*;

public class UCSMode extends GameMode implements SearchAlgorithm {
    public UCSMode(GameController gameController) {
        super("UCS Mode", gameController);
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
        Queue<State> priorityQueue = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(State o1, State o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        source.setWeight(this.gameController.getDistanceFromGoal(source));
        priorityQueue.add(source);
        source.addToPath(source);
        int x = source.getHorse().getCoordinate().getX();
        int y = source.getHorse().getCoordinate().getY();
        visited[x][y] = true;
        while (!priorityQueue.isEmpty()) {
            State state = priorityQueue.poll();
            if(goal.equals(state.getHorse().getCoordinate())) {
                for (State state1 : state.getPath()) {
                    this.gameController.addState(state1);
                }
                break;
            }
            List<State> availableStates = this.gameController.getAvailableNextStates(state);
            for(State nextState : availableStates) {
                // calculate the weight of the nextState
                int dist = this.gameController.getDistanceFromGoal(nextState);
                nextState.setWeight(dist + state.getWeight());

                int xs = nextState.getHorse().getCoordinate().getX();
                int ys = nextState.getHorse().getCoordinate().getY();
                if(!visited[xs][ys]) {
                    priorityQueue.add(nextState);
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
