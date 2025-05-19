package manfrinmarco.events;

import manfrinmarco.core.GameContext;

public class ScoreListener implements EventListener {
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType().equals("enemy_defeated")) {
            int score = Integer.parseInt(manfrinmarco.config.GameConfig.get("score.enemy.defeated"));
            GameContext.getInstance().increaseScore(score);
        }
    }
}