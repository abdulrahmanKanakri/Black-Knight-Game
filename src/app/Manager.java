package app;

import app.config.Config;
import app.controllers.GameController;
import app.enums.CellType;
import app.enums.Colors;
import app.models.Cell;
import app.models.Coordinate;
import app.models.Game;
import app.models.Horse;
import app.models.gamemodes.*;
import app.repositories.config.ConfigRepoImpl;
import app.repositories.config.IConfigRepo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Utils;

import java.util.List;

public class Manager {
    private final Stage stage;
    private final IConfigRepo iConfigRepo;
    private final GameController gameController;

    public Manager(Stage stage) {
        this.stage = stage;
        this.iConfigRepo = new ConfigRepoImpl();
        this.gameController = new GameController();
    }

    public void showMenusPage() {
        Button buttonDefault = new Button("Use Default Game Values");
        Button buttonCustomize = new Button("Customize Game Values");
        buttonDefault.setMinWidth(160);
        buttonCustomize.setMinWidth(160);
        buttonDefault.setOnMouseClicked(event -> {
            this.iConfigRepo.setConfigData(Config.getDefaultConfig());
            this.showStartPage();
        });
        buttonCustomize.setOnMouseClicked(event -> this.showConfigPage());

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(buttonDefault, buttonCustomize);

        this.stage.setTitle(Game.title + " - Menus Page");
        this.stage.setScene(new Scene(vBox, 245, 400));
    }

    public void showConfigPage() {
        Config config = this.iConfigRepo.getConfigData();

        // Board Width
        Text textBoardWidth = new Text("Board Width");
        TextField textFieldBoardWidth = new TextField(Integer.toString(config.getN()));

        // Board Height
        Text textBoardHeight = new Text("Board Height");
        TextField textFieldBoardHeight = new TextField(Integer.toString(config.getM()));

        // Horse X
        Text textHorseX = new Text("Horse X");
        TextField textFieldHorseX = new TextField(Integer.toString(config.getHorseCoordinate().getX()));

        // Horse Y
        Text textHorseY = new Text("Horse Y");
        TextField textFieldHorseY = new TextField(Integer.toString(config.getHorseCoordinate().getY()));

        // Goal X
        Text textGoalX = new Text("Goal X");
        TextField textFieldGoalX = new TextField(Integer.toString(config.getExitHoleCoordinate().getX()));

        // Goal Y
        Text textGoalY = new Text("Goal Y");
        TextField textFieldGoalY = new TextField(Integer.toString(config.getExitHoleCoordinate().getY()));

        // Creating Button
        Button btnUpdateValues = new Button("Update Values");
        btnUpdateValues.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnUpdateValues.setOnMouseClicked(event -> {
            this.iConfigRepo.setConfigData(new Config(
                Utils.initCells(
                    Integer.parseInt(textFieldBoardWidth.getText()),
                    Integer.parseInt(textFieldBoardHeight.getText())
                ),
                new Coordinate(
                    Integer.parseInt(textFieldHorseX.getText()),
                    Integer.parseInt(textFieldHorseY.getText())
                ),
                new Coordinate(
                    Integer.parseInt(textFieldGoalX.getText()),
                    Integer.parseInt(textFieldGoalY.getText())
                ),
                Integer.parseInt(textFieldBoardWidth.getText()),
                Integer.parseInt(textFieldBoardHeight.getText())
            ));
            // Show success alert
            Alert alert = new Alert(
                Alert.AlertType.NONE,
                "Config Successfully updated \n Now you should choose the empty cells",
                ButtonType.OK
            );
            alert.setTitle("Congratulations \uD83C\uDF89");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                this.showChooseEmptyCellsPage();
            }
        });

        // Grid pane to hold all config elements
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Arranging all the nodes in the grid
        gridPane.add(textBoardWidth, 0, 0);
        gridPane.add(textFieldBoardWidth, 1, 0);

        gridPane.add(textBoardHeight, 0, 1);
        gridPane.add(textFieldBoardHeight, 1, 1);

        gridPane.add(textHorseX, 0, 2);
        gridPane.add(textFieldHorseX, 1, 2);

        gridPane.add(textHorseY, 0, 3);
        gridPane.add(textFieldHorseY, 1, 3);

        gridPane.add(textGoalX, 0, 4);
        gridPane.add(textFieldGoalX, 1, 4);

        gridPane.add(textGoalY, 0, 5);
        gridPane.add(textFieldGoalY, 1, 5);

        gridPane.add(btnUpdateValues, 0, 7, 3, 1);
        this.stage.setTitle(Game.title + " - Config Page");
        this.stage.setScene(new Scene(gridPane, 245, 400));
    }

    public void showChooseEmptyCellsPage() {
        int width = 640;
        int height = 480;
        Config config = iConfigRepo.getConfigData();
        List<Cell> cells = config.getCells();
        Horse horse = new Horse(config.getHorseCoordinate());
        Coordinate exitHole = config.getExitHoleCoordinate();

        if(cells.size() > 36) {
            width = 1200;
            height = 720;
            this.stage.setX(100);
            this.stage.setY(50);
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);

        Button btnContinue = new Button("Continue");
        btnContinue.setMinWidth(70);
        btnContinue.setMinHeight(30);
        btnContinue.setOnMouseClicked(event -> {
            config.setCells(cells);
            iConfigRepo.setConfigData(config);
            gridPane.getChildren().removeAll();
            this.showStartPage();
        });

        gridPane.add(btnContinue, config.getN(), config.getM() + 2);

        for (Cell cell : cells) {
            Button button = new Button(cell.getType().toString());
            button.setMinWidth(70);
            button.setMinHeight(70);
            button.setStyle("-fx-background-color:" + Colors.CELL_TILE.getHexValue() + ";");

            if(exitHole.equals(cell.getCoordinate())) {
                cell.setType(CellType.EXIT_HOLE);
                button.setText(CellType.EXIT_HOLE.toString());
                button.setStyle("-fx-background-color:" + Colors.CELL_EXIT_HOLE.getHexValue() + ";");
            } else if(horse.getCoordinate().equals(cell.getCoordinate())) {
                cell.setType(CellType.HORSE);
                button.setText(CellType.HORSE.toString());
                button.setStyle("-fx-background-color:" + Colors.HORSE.getHexValue() + ";");
            } else {
                button.setOnMouseClicked(event -> {
                    if(cell.getType().equals(CellType.TILE)) {
                        cell.setType(CellType.EMPTY);
                        button.setText(CellType.EMPTY.toString());
                        button.setStyle("-fx-background-color:" + Colors.CELL_EMPTY.getHexValue() + ";");
                    } else {
                        cell.setType(CellType.TILE);
                        button.setText(CellType.TILE.toString());
                        button.setStyle("-fx-background-color:" + Colors.CELL_TILE.getHexValue() + ";");
                    }
                });
            }

            // button, col, row
            gridPane.add(button, cell.getCoordinate().getX(), cell.getCoordinate().getY());
        }

        this.stage.setTitle(Game.title + " - Choose Empty Cells");
        this.stage.setScene(new Scene(gridPane, width, height));
    }

    public void showStartPage() {
        Button buttonHuman = new Button("Play as Human");
        Button buttonDFS = new Button("Play as DFS");
        Button buttonBFS = new Button("Play as BFS");
        Button buttonUCS = new Button("Play as UCS");
        Button buttonAStar = new Button("Play as A*");
        Button buttonHillClimb = new Button("Play as Hill Climb");

        buttonHuman.setMinWidth(120);
        buttonDFS.setMinWidth(120);
        buttonBFS.setMinWidth(120);
        buttonUCS.setMinWidth(120);
        buttonAStar.setMinWidth(120);
        buttonHillClimb.setMinWidth(120);

        buttonHuman.setOnMouseClicked(event -> this.startGame(new HumanMode(this.gameController)));
        buttonDFS.setOnMouseClicked(event -> this.startGame(new DFSMode(this.gameController)));
        buttonBFS.setOnMouseClicked(event -> this.startGame(new BFSMode(this.gameController)));
        buttonUCS.setOnMouseClicked(event -> this.startGame(new UCSMode(this.gameController)));
        buttonAStar.setOnMouseClicked(event -> this.startGame(new AStarMode(this.gameController)));
        buttonHillClimb.setOnMouseClicked(event -> this.startGame(new HillClimbMode(this.gameController)));

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
            buttonHuman, buttonDFS, buttonBFS, buttonUCS, buttonHillClimb, buttonAStar
        );

        this.stage.setTitle(Game.title + " - Start Page");
        this.stage.setScene(new Scene(vBox, 275, 400));
    }

    public void startGame(GameMode gameMode) {
        gameMode.run();
        this.stage.setTitle(Game.title + " - " + gameMode.getModeTitle());
        this.stage.setScene(this.gameController.getGameView().getScene());
    }
}
