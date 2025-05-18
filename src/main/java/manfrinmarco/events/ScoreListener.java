package manfrinmarco.events;

import manfrinmarco.core.GameContext;

public class ScoreListener implements EventListener {
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType().equals("enemy_defeated")) {
            GameContext.getInstance().increaseScore(100);
        }
    }
}