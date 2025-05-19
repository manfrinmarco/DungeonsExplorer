package manfrinmarco.core;

import java.io.Serializable;

public class GameStateMemento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final GameContext snapshot;

    public GameStateMemento(GameContext current) {
        this.snapshot = current.cloneContext();
    }

    public GameContext getSnapshot() {
        return snapshot;
    }
}