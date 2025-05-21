package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Orge extends Enemy {
    public Orge() {
        super("Orco", 45, new AggressiveStrategy());
    }
}