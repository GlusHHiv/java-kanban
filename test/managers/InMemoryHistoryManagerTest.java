package managers;

import static org.junit.jupiter.api.Assertions.*;

import classes.Status;
import classes.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

class InMemoryHistoryManagerTest {
    private static final Managers managers = new Managers();
    private static final Task task = new Task("Test addNewTask", "Test addNewTask description", 0, Status.NEW);

    @BeforeAll
    public static void beforeAll() {
        managers.getDefault().createTask(task);
        managers.getDefaultHistory().add(task);
    }

    
    @Test
    public void add() {
        final List<Task> history = managers.getDefaultHistory().getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void compareTasksInHistoryAndManager() {
        Assertions.assertEquals(task, managers.getDefaultHistory().getHistory().get(0), "Задачи не равны");
    }
}