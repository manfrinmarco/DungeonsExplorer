package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Demon extends Enemy {
    public Demon() {
        super("Demone", 60, new AggressiveStrategy());
    }
}
