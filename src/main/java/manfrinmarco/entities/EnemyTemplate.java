package manfrinmarco.entities;

public interface EnemyTemplate {
    String getName();
    int getHealth();
    EnemyStrategy getStrategy();
    
    default Enemy create() {
        return new Enemy(getName(), getHealth(), getStrategy());
    }
}