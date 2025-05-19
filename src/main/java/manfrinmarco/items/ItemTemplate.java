package manfrinmarco.items;

public interface ItemTemplate {
    Item create();
    String getId(); // es: "spada", "pozione", ecc.
}