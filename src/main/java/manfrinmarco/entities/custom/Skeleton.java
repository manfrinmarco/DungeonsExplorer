package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Skeleton extends Enemy {
    public Skeleton() {
        super("Scheletro", 20, new DefensiveStrategy());
    }
}
