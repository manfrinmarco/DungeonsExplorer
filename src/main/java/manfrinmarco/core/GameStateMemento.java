package manfrinmarco.core;

public class GameStateMemento {
    private final String state;

    public GameStateMemento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}