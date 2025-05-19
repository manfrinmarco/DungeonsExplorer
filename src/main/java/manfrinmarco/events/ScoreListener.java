package manfrinmarco.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.core.GameContext;

public class ScoreListener implements EventListener {
    private static final Logger log = Logger.getLogger(ScoreListener.class.getName());
    @Override
    public void onEvent(GameEvent event) {
        log.log(Level.FINE, "ScoreListener: ricevuto evento di tipo {0}", event.getType());
        if (event.getType().equals("enemy_defeated")) {
            log.info("ScoreListener: nemico sconfitto, incremento punteggio");
            int score = Integer.parseInt(manfrinmarco.config.GameConfig.get("score.enemy.defeated"));
            GameContext.getInstance().increaseScore(score);
        }
    }
}