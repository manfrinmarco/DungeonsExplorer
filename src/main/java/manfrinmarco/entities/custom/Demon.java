package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Demon implements EnemyTemplate {
    @Override
    public String getName() {
        return "Demone";
    }

    @Override
    public int getHealth() {
        return 60;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new AggressiveStrategy();
    }
}