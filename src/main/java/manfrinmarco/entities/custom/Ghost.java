package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Ghost implements EnemyTemplate {
    @Override
    public String getName() {
        return "Spettro";
    }

    @Override
    public int getHealth() {
        return 35;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new DefensiveStrategy();
    }
}