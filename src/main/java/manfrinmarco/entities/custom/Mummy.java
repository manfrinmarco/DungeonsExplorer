package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.EnemyStrategy;
import manfrinmarco.entities.EnemyTemplate;

@AutoLoad
public class Mummy implements EnemyTemplate {
    @Override
    public String getName() {
        return "Mummia";
    }

    @Override
    public int getHealth() {
        return 30;
    }

    @Override
    public EnemyStrategy getStrategy() {
        return new DefensiveStrategy();
    }
}