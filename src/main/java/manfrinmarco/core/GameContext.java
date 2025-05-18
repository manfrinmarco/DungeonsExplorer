package manfrinmarco.core;

public class GameContext {
    private static GameContext instance = null;

    private boolean gameOver = false;

    private GameContext() {}

    public static GameContext getInstance() {
        if (instance == null) instance = new GameContext();
        return instance;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        gameOver = true;
    }
}
