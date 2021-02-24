package app.models.gamemodes;

import app.config.Config;
import app.controllers.GameController;
import app.models.*;

import java.util.List;

public class HumanMode extends GameMode {
    public HumanMode(GameController gameController) {
        super("Human Mode", gameController);
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

        this.gameController.addState(state);
        this.gameController.initView(state);
    }
}
