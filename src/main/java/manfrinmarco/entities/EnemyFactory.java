package manfrinmarco.entities;

public class EnemyFactory {
    public static Enemy createEnemy(String type) {
        return switch (type.toLowerCase()) {
            case "goblin" -> new Enemy("Goblin", 30, new AggressiveStrategy());
            case "skeleton" -> new Enemy("Scheletro", 25, new DefensiveStrategy());
            default -> new Enemy("Ratto", 15, new AggressiveStrategy());
        };
    }
}