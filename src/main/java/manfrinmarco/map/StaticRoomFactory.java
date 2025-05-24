package manfrinmarco.map;

@Deprecated
public class StaticRoomFactory {
    public static Room createRoom(String type) {
        return switch (type.toLowerCase()) {
            case "corridoio" -> new Room("Corridoio", "Un lungo corridoio buio.");
            case "cella" -> new Room("Cella", "Una piccola cella abbandonata.");
            case "entrata" -> new Room("Entrata", "L'ingresso del castello.");
            case "sala" -> new Room("Sala del Trono", "La grande sala del trono.");
            case "armeria" -> new Room("Armeria Oscura", "Contiene armi abbandonate.");
            case "cripta" -> new Room("Cripta", "Una cripta buia e fredda.");
            default -> new Room("Stanza misteriosa", "Non riesci a distinguere nulla.");
        };
    }
}