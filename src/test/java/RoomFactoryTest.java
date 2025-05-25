import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import manfrinmarco.map.Room;
import manfrinmarco.map.RoomFactory;
import manfrinmarco.security.GameException;

public class RoomFactoryTest {

    @Test
    public void testCreateKnownRoom() {
        Room room = RoomFactory.createRoom("nave"); // esempio: @AutoLoad nave
        assertNotNull(room);
        assertEquals("Nave", room.getName());
    }

    @Test
    public void testCreateUnknownRoom() {
        Exception ex = assertThrows(GameException.class, () -> RoomFactory.createRoom("spazio"));
        assertTrue(ex.getMessage().contains("Tipo stanza non trovato"));
    }
}