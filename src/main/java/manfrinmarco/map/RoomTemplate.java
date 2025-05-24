package manfrinmarco.map;

public interface RoomTemplate {
    String getType();  // es: "corridoio", "cella", ecc.
    Room create();
}