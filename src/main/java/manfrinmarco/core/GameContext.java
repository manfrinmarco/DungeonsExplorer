package manfrinmarco.core;

public class GameContext {
    private static final GameContext instance = new GameContext();

    private GameContext() {}

    public static GameContext getInstance() {
        return instance;
    }
}
