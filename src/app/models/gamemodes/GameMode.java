package app.models.gamemodes;

import app.controllers.GameController;
import app.repositories.config.ConfigRepoImpl;
import app.repositories.config.IConfigRepo;

abstract public class GameMode {
    protected IConfigRepo iConfigRepo = new ConfigRepoImpl();
    protected String modeTitle;
    protected GameController gameController;

    public GameMode(String modeTitle, GameController gameController) {
        this.modeTitle = modeTitle;
        this.gameController = gameController;
    }

    abstract public void run();

    public String getModeTitle() {
        return modeTitle;
    }
}
