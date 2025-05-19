package manfrinmarco.core;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.entities.Player;
import manfrinmarco.events.EventManager;
import manfrinmarco.map.Room;

public class GameContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final GameContext instance = new GameContext();
    private static final Logger log = Logger.getLogger(GameContext.class.getName());

    private Player player;
    private Room currentRoom;
    private int score;
    private transient EventManager eventManager = new EventManager();

    private GameContext() {
        // usato solo dal singleton
    }
    
    public static GameContext getInstance() {
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public EventManager getEventManager() {
        if (eventManager == null) {
            log.fine("Reinizializzazione dell'EventManager dopo deserializzazione");
            eventManager = new EventManager();
        }
        return eventManager;
    }
    
    /**
     * Permette di sostituire l'EventManager,
     * utile per riallacciare i listener dopo il load.
     */
    public void setEventManager(EventManager manager) {
        log.fine("EventManager sostituito tramite setEventManager");
        this.eventManager = manager;
    }
    

    public void increaseScore(int amount) {
        log.log(Level.INFO, "Incremento punteggio di {0} punti; nuovo punteggio: {1}{2}", new Object[]{amount, this.score, amount});
        this.score += amount;
        System.out.println("+" + amount + " punti! Punteggio attuale: " + this.score);
    }

    public int getScore() {
        return score;
    }

    public void copyFrom(GameContext other) {
        this.player = other.player;
        this.currentRoom = other.currentRoom;
        this.score = other.score;
    }

    public GameContext cloneContext() {
        GameContext clone = new GameContext();
        clone.copyFrom(this);
        return clone;
    }
}