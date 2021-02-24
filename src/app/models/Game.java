package app.models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<State> states;
    private static Game singleton = null;
    public static final String title = "Black Night";

    private Game() {
        this.states = new ArrayList<>();
    }

    public static Game getInstance() {
        if(singleton == null) {
            singleton = new Game();
        }
        return singleton;
    }

    public List<State> getStates() {
        return states;
    }

    public void addState(State state) {
        this.states.add(state);
    }
}
