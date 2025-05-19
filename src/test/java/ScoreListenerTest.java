import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import manfrinmarco.core.GameContext;
import manfrinmarco.events.GameEvent;
import manfrinmarco.events.ScoreListener;

public class ScoreListenerTest {

    private ScoreListener listener;

    @BeforeEach
    public void setUp() {
        listener = new ScoreListener();
    }

    @Test
    public void testOnEnemyDefeatedEvent() {
        GameEvent event = new GameEvent("enemy_defeated", "Nemico sconfitto");

        GameContext mockContext = mock(GameContext.class);
        try {
            java.lang.reflect.Field instanceField = GameContext.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, mockContext);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject mock GameContext", e);
        }

        listener.onEvent(event);

        verify(mockContext, times(1)).increaseScore(100);
    }

    @Test
    public void testOnNonEnemyEventDoesNothing() {
        GameEvent event = new GameEvent("item_picked", "Hai preso un oggetto");

        listener.onEvent(event);

        // No exceptions should be thrown and nothing should happen
    }
}
