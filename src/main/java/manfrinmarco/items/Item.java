package manfrinmarco.items;

import java.io.Serializable;

public class Item implements Serializable{
    private static final long serialVersionUID = 1L;
    protected String name;
    protected ItemType type;
    protected int power;
    protected boolean  combinable = false;
    

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public Item(String name, ItemType type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    @Override
    public String toString() {
        return name + " (" + type + (power > 0 ? ", +" + power : "") + ")";
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public void setCombinable(boolean value){
        this.combinable = value;
    }

    public boolean isCombinable(){
        return this.combinable;
    }
}