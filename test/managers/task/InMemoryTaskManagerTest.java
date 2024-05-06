package managers.task;

import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import org.junit.jupiter.api.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    private static InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    static Epic epic;

    private InMemoryTaskManagerTest() {
        super(taskManager);
    }

}
