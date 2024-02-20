package managers;

import managers.history.InMemoryHistoryManager;
import managers.task.InMemoryTaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ManagersTest {
    private static final Managers managers = new Managers();
    @BeforeAll
    public static void beforeAll() {
        managers.getDefault().createTask(new Task("212", "dsd", 0, Status.NEW));

    }

    @Test
    void shouldReturnTheSameName() {
        Assertions.assertEquals(managers.getDefault().getTaskById(0).getName(), new InMemoryTaskManager(new InMemoryHistoryManager()).getTaskById(0).getName());
    }

    @Test
    void shouldReturnHistorySizeOf2WhenOneTaskCalledTwice() {
        Assertions.assertEquals(managers.getDefaultHistory().getHistory().size(), 2);
    }
}