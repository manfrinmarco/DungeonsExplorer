package manfrinmarco.map;

public class RoomFactory {
    public static Room createRoom(String type) {
        return switch (type.toLowerCase()) {
            case "corridoio" -> new Room("Corridoio", "Un lungo corridoio buio.");
            case "cella" -> new Room("Cella", "Una piccola cella abbandonata.");
            default -> new Room("Stanza misteriosa", "Non riesci a distinguere nulla.");
        };
    }
}