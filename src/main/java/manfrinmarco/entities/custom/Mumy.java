package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Mumy extends Enemy {
    public Mumy() {
        super("Mummia", 30, new DefensiveStrategy());
    }
}