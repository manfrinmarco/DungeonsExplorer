import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import manfrinmarco.events.EventListener;
import manfrinmarco.events.EventManager;
import manfrinmarco.events.GameEvent;

public class EventManagerTest {

    @Test
    public void testNotifyListeners() {
        EventListener listener = mock(EventListener.class);
        GameEvent event = new GameEvent("testType", "testData");

        EventManager eventManager = new EventManager();
        eventManager.subscribe(listener);
        eventManager.notify(event);

        verify(listener, times(1)).onEvent(event);
    }
}