package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;


@AutoLoad
public class Spettro extends Enemy {
    public Spettro() {
        super("Spettro", 35, new DefensiveStrategy());
    }
}
