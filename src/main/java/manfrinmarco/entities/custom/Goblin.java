package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 30, new AggressiveStrategy());
    }
}
