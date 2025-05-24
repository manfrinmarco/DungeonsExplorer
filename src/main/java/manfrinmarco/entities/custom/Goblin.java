package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Goblin implements EnemyTemplate {
    @Override
    public String getName() {
        return "Goblin";
    }

    @Override
    public int getHealth() {
        return 30;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new AggressiveStrategy();
    }
}