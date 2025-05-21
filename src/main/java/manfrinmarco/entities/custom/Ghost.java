package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;


@AutoLoad
public class Ghost extends Enemy {
    public Ghost() {
        super("Spettro", 35, new DefensiveStrategy());
    }
}
