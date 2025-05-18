package manfrinmarco.entities.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;

@AutoLoad
public class Mummia extends Enemy {
    public Mummia() {
        super("Mummia", 30, new DefensiveStrategy());
    }
}