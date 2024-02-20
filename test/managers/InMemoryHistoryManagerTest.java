package managers;

import static org.junit.jupiter.api.Assertions.*;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

class InMemoryHistoryManagerTest {
    private static final InMemoryHistoryManager managers = new InMemoryHistoryManager();
    private static final Task task = new Task("Test addNewTask", "Test addNewTask description", 0, Status.NEW);

    @BeforeAll
    public static void beforeAll() {
        managers.add(task);
    }

    
    @Test
    public void shouldConsist1ElementInHistory() {
        final List<Task> history = managers.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void shouldBeFilledWithOneTask() {
        Assertions.assertEquals(task, managers.getHistory().get(0), "Задачи не равны");
    }
}