package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Orge implements EnemyTemplate {
    @Override
    public String getName() {
        return "Orco";
    }

    @Override
    public int getHealth() {
        return 45;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new AggressiveStrategy();
    }
}