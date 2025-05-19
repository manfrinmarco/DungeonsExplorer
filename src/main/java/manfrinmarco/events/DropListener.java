package manfrinmarco.events;

import manfrinmarco.core.GameContext;
import manfrinmarco.entities.Enemy;
import manfrinmarco.items.Item;
import manfrinmarco.map.Room;

public class DropListener implements EventListener {
    @Override
    public void onEvent(GameEvent event) {
        if (!"enemy_defeated".equals(event.getType())) return;
        Object p = event.getPayload();
        if (p instanceof Enemy enemy) {
            Item drop = enemy.getDrop();
            if (drop != null) {
                Room room = GameContext.getInstance().getCurrentRoom();
                room.addItem(drop);
                System.out.println("Il " + enemy.getName() + " lascia cadere: " + drop.getName());
            }
        }
    }
}