package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Orco extends Enemy {
    public Orco() {
        super("Orco", 45, new AggressiveStrategy());
    }
}