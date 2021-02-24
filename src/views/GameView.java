package views;

import app.controllers.GameController;
import app.enums.CellType;
import app.models.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import app.enums.Colors;
import java.util.List;

public class GameView {
    private int width = 1200;
    private int height = 720;
    private final GridPane view = new GridPane();
    private final GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    public void init(State state) {
        this.view.setHgap(2);
        this.view.setVgap(2);
        this.view.setAlignment(Pos.CENTER);
        this.update(state);
    }

    public void update(State state) {
        Board board = state.getBoard();
        List<Cell> cells = board.getCells();
        Horse horse = state.getHorse();
        Coordinate exitHole = board.getExitHole();

        this.view.getChildren().removeAll();

        for (Cell cell : cells) {
            Button button = new Button(cell.getType().toString());
            button.setMinWidth(70);
            button.setMinHeight(70);
            switch (cell.getType()) {
                case EMPTY:
                    button.setStyle("-fx-background-color:" + Colors.CELL_EMPTY.getHexValue() + ";");
                    break;
                case EXIT_HOLE:
                    button.setStyle("-fx-background-color:" + Colors.CELL_EXIT_HOLE.getHexValue() + ";");
                    break;
                default:
                    button.setStyle("-fx-background-color:" + Colors.CELL_TILE.getHexValue() + ";");
            }

            if(exitHole.equals(cell.getCoordinate())) {
                cell.setType(CellType.EXIT_HOLE);
                button.setText(CellType.EXIT_HOLE.toString());
                button.setStyle("-fx-background-color:" + Colors.CELL_EXIT_HOLE.getHexValue() + ";");
            } else if(horse.getCoordinate().equals(cell.getCoordinate())) {
                cell.setType(CellType.TILE);
                button.setText(CellType.HORSE.toString());
                button.setStyle("-fx-background-color:" + Colors.HORSE.getHexValue() + ";");
                button.setOnMouseClicked(event -> this.gameController.showAvailableNextStates(
                    this.view,
                    this.gameController.getAvailableNextStates(state)
                ));
            }

            // button, col, row
            this.view.add(button, cell.getCoordinate().getX(), cell.getCoordinate().getY());
        }
    }

    public void printStatesPath() {
        List<State> states = this.gameController.getGame().getStates();
        Board board = states.get(0).getBoard();
        List<Cell> cells = board.getCells();
        this.view.setHgap(2);
        this.view.setVgap(2);
        this.view.setAlignment(Pos.CENTER);
        for (Cell cell : cells) {
            Button button = new Button(cell.getType().toString());
            button.setMinWidth(70);
            button.setMinHeight(70);
            switch (cell.getType()) {
                case EMPTY:
                    button.setStyle("-fx-background-color:" + Colors.CELL_EMPTY.getHexValue() + ";");
                    break;
                case EXIT_HOLE:
                    button.setStyle("-fx-background-color:" + Colors.CELL_EXIT_HOLE.getHexValue() + ";");
                    break;
                default:
                    button.setStyle("-fx-background-color:" + Colors.CELL_TILE.getHexValue() + ";");
            }

            // button, col, row
            this.view.add(button, cell.getCoordinate().getX(), cell.getCoordinate().getY());
        }

        for(Node child : this.view.getChildren()) {
            Button button = (Button) child;
            for(int i = 0; i < states.size(); i++) {
                State state = states.get(i);
                int horseX = state.getHorse().getCoordinate().getX();
                int horseY = state.getHorse().getCoordinate().getY();
                if(horseY == GridPane.getRowIndex(button) && horseX == GridPane.getColumnIndex(button)) {
                    button.setStyle("-fx-background-color:" + Colors.PATH.getHexValue() + ";");
                    button.setText(Integer.toString(i + 1));
                }
            }
        }
    }

    public Scene getScene() {
        return new Scene(this.view, this.width, this.height);
    }
}
