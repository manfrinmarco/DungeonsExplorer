package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Skeleton implements EnemyTemplate {
    @Override
    public String getName() {
        return "Scheletro";
    }

    @Override
    public int getHealth() {
        return 20;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new DefensiveStrategy();
    }
}