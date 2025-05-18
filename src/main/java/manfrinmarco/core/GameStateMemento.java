package manfrinmarco.core;

public class GameStateMemento {
    private final GameContext snapshot;

    public GameStateMemento(GameContext current) {
        this.snapshot = current.cloneContext();
    }

    public GameContext getSnapshot() {
        return snapshot;
    }
}