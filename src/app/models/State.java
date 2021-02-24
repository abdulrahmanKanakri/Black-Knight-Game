package app.models;

import app.interfaces.Copyable;

import java.util.ArrayList;
import java.util.List;

public class State implements Copyable {
    private final Board board;
    private final Horse horse;
    private List<State> path = new ArrayList<>();
    private int weight = 1;

    public State(Board board, Horse horse) {
        this.board = board;
        this.horse = horse;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Board getBoard() {
        return board;
    }

    public Horse getHorse() {
        return horse;
    }

    public List<State> getPath() {
        return path;
    }

    public void addToPath(State state) {
        this.path.add(state);
    }

    @Override
    public State getCopy() {
        return new State(this.board.getCopy(), this.horse.getCopy());
    }

    @Override
    public String toString() {
        return "State{" +
                ", horseX=" + horse.getCoordinate().getX() +
                ", horseY=" + horse.getCoordinate().getY() +
                '}';
    }
}
