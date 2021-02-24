package app.controllers;

import app.enums.CellType;
import app.models.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import views.GameView;
import app.enums.Colors;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Game game;
    private final GameView gameView;

    public GameController() {
        this.game = Game.getInstance();
        this.gameView = new GameView(this);
    }

    public Game getGame() {
        return game;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void addState(State state) {
        this.game.getStates().add(state);
    }

    public void initView(State state) {
        this.gameView.init(state);
    }

    public void printStatesPath() {
        this.gameView.printStatesPath();
    }

    public List<State> getAvailableNextStates(State state) {
        // RU
        // state 1-1: {x + 1, y}, {x + 2, y}, {x + 2, y - 1}
        // state 1-2: {x, y - 1}, {x + 1, y - 1}, {x + 2, y - 1}

        // RD
        // state 2-1: {x + 1, y}, {x + 2, y}, {x + 2, y + 1}
        // state 2-2: {x, y + 1}, {x + 1, y + 1}, {x + 2, y + 1}

        // LU
        // state 1-1: {x - 1, y}, {x - 2, y}, {x - 2, y - 1}
        // state 1-2: {x, y - 1}, {x - 1, y - 1}, {x - 2, y - 1}

        // LD
        // state 2-1: {x - 1, y}, {x - 2, y}, {x - 2, y + 1}
        // state 2-2: {x, y + 1}, {x - 1, y + 1}, {x - 2, y + 1}

        // UR
        // state 1-1: {x, y - 1}, {x, y - 2}, {x + 1, y - 2}
        // state 1-2: {x + 1, y}, {x + 1, y - 1}, {x + 1, y - 2}

        // UL
        // state 2-1: {x, y - 1}, {x, y - 2}, {x - 1, y - 2}
        // state 2-2: {x - 1, y}, {x - 1, y - 1}, {x - 1, y - 2}

        // DR
        // state 1-1: {x, y + 1}, {x, y + 2}, {x + 1, y + 2}
        // state 1-2: {x + 1, y}, {x + 1, y + 1}, {x + 1, y + 2}

        // DL
        // state 2-1: {x, y + 1}, {x, y + 2}, {x - 1, y + 2}
        // state 2-2: {x - 1, y}, {x - 1, y + 1}, {x - 1, y + 2}

        int[][] extX = new int[][]{
                {1, 2, 2, 0, 1, 2},       // RD
                {1, 2, 2, 0, 1, 2},       // RU

                {-1, -2, -2, 0, -1, -2},  // LD
                {-1, -2, -2, 0, -1, -2},  // LU

                {0, 0, 1, 1, 1, 1},       // DR
                {0, 0, -1, -1, -1, -1},   // DL

                {0, 0, 1, 1, 1, 1},       // UR
                {0, 0, -1, -1, -1, -1},   // UL
        };

        int[][] extY = new int[][]{
                {0, 0, 1, 1, 1, 1},       // RD
                {0, 0, -1, -1, -1, -1},   // RU

                {0, 0, 1, 1, 1, 1},       // LD
                {0, 0, -1, -1, -1, -1},   // LU

                {1, 2, 2, 0, 1, 2},       // DR
                {1, 2, 2, 0, 1, 2},       // DL

                {-1, -2, -2, 0, -1, -2},  // UR
                {-1, -2, -2, 0, -1, -2},  // UL
        };

        // state 1-2: x+=2, y++ || y--
        // state 3-4: x-=2, y++ || y--
        // state 5-6: y+=2, x++ || x--
        // state 7-8: y-=2, x++ || x--

        // RD RU LD LU DR DL UR UL
        int[] xs = new int[]{2, 2, -2, -2, 1, -1, 1, -1};
        int[] ys = new int[]{1, -1, 1, -1, 2, 2, -2, -2};

        int x = state.getHorse().getCoordinate().getX();
        int y = state.getHorse().getCoordinate().getY();
        List<State> states = new ArrayList<>();

        for (int i = 0; i < xs.length; i++) {
            boolean res1 = this.checkBoardLimitsMulti(
                    state.getBoard(),
                    new int[]{ extX[i][0] + x, extX[i][1] + x, extX[i][2] + x },
                    new int[]{ extY[i][0] + y, extY[i][1] + y, extY[i][2] + y }
            );
            boolean res2 = this.checkBoardLimitsMulti(
                    state.getBoard(),
                    new int[]{ extX[i][3] + x, extX[i][4] + x, extX[i][5] + x },
                    new int[]{ extY[i][3] + y, extY[i][4] + y, extY[i][5] + y }
            );
            if(res1 || res2) {
                State state1 = state.getCopy();
//                int dist = this.getDistanceFromGoal(state1);
//                state1.setWeight(dist + state.getWeight());
                state1.getHorse().setCoordinate(new Coordinate(x + xs[i], y + ys[i]));
                states.add(state1);
            }
        }

        return states;
    }

    public void showAvailableNextStates(GridPane gridPane, List<State> states) {
        for(Node child : gridPane.getChildren()) {
            for(State state : states) {
                int horseX = state.getHorse().getCoordinate().getX();
                int horseY = state.getHorse().getCoordinate().getY();
                if(horseY == GridPane.getRowIndex(child) && horseX == GridPane.getColumnIndex(child)) {
                    child.setStyle("-fx-background-color:" + Colors.AVAILABLE_STATE.getHexValue() + ";");
                    child.setOnMouseClicked(event -> {
                        this.addState(state);
                        this.gameView.update(state);
                        if(checkIfReachedTheExitHole(state.getBoard(), horseX, horseY)) {
                            Alert alert = new Alert(
                                Alert.AlertType.INFORMATION,
                                "Congratulations you solved the game \uD83C\uDF89",
                                ButtonType.CLOSE
                            );
                            alert.setTitle("Congratulations \uD83C\uDF89");
                            alert.showAndWait();
                            if(alert.getResult() == ButtonType.CLOSE) {
                                System.exit(0);
                            }
                        }
                    });
                }
            }
        }
    }

    private boolean checkBoardLimitsMulti(Board board, int[] xs, int[] ys) {
        for (int i = 0; i < xs.length; i++) {
            int x = xs[i];
            int y = ys[i];
            if(x <= board.getN() && x >= 1 && y <= board.getM() && y >= 1) {
                for(Cell cell : board.getCells()) {
                    if (
                        cell.getCoordinate().getX() == x &&
                        cell.getCoordinate().getY() == y &&
                        cell.getType() == CellType.EMPTY
                    ) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfReachedTheExitHole(Board board, int x, int y) {
        for(Cell cell : board.getCells()) {
            if (
                cell.getCoordinate().getX() == x &&
                cell.getCoordinate().getY() == y &&
                cell.getType() == CellType.EXIT_HOLE
            ) {
                return true;
            }
        }
        return false;
    }

    public int getDistanceFromGoal(State state) {
        return Math.abs(state.getHorse().getCoordinate().getX() - 4) +
                Math.abs(state.getHorse().getCoordinate().getY() - 2);
    }
}
