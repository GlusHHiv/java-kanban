package managers.task;

import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    private static InMemoryTaskManager taskManager;
    private static InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

    private InMemoryTaskManagerTest() {
        super(manager);
    }

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
        manager.deleteAllTasks();
        manager.deleteAllSubtasks();
        manager.deleteAllEpics();
        manager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        epicId = manager.getEpics().get(0).getId();
        manager.createSubTask((new Subtask("Test Sub",
                "Test",
                Status.NEW,
                0,
                epicId,
                20,
                now)));
        manager.createTask(new Task("Test task",
                "test description",
                0,
                Status.NEW,
                40,
                taskTime));
        taskId = manager.findTaskIdByName("Test task");
        subId = manager.findTaskIdByName("Test Sub");
    }

    @Test
    public void EpicNewStatusTest() {

        taskManager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        taskManager.createSubTask((new Subtask("Test Sub1",
                "Test",
                Status.NEW,
                0,
                0,
                20,
                LocalDateTime.now())));

        taskManager.createSubTask((new Subtask("Test Sub2",
                "Test",
                Status.NEW,
                0,
                0,
                40,
                LocalDateTime.now().plusHours(1))));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(), Status.NEW);
    }

    @Test
    public void EpicDoneStatusTest() {
        taskManager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        taskManager.createSubTask((new Subtask("Test Sub1",
                "Test",
                Status.DONE,
                0,
                0,
                20,
                LocalDateTime.now())));

        taskManager.createSubTask((new Subtask("Test Sub2",
                "Test",
                Status.DONE,
                0,
                0,
                40,
                LocalDateTime.now().plusHours(1))));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(), Status.DONE);
    }

    @Test
    public void EpicNewAndDoneStatusTest() {
        taskManager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        taskManager.createSubTask((new Subtask("Test Sub1",
                "Test",
                Status.DONE,
                0,
                0,
                20,
                LocalDateTime.now())));

        taskManager.createSubTask((new Subtask("Test Sub2",
                "Test",
                Status.NEW,
                0,
                0,
                40,
                LocalDateTime.now().plusHours(1))));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void EpicInProgressStatusTest() {
        taskManager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        taskManager.createSubTask((new Subtask("Test Sub1",
                "Test",
                Status.IN_PROGRESS,
                0,
                0,
                20,
                LocalDateTime.now())));

        taskManager.createSubTask((new Subtask("Test Sub2",
                "Test",
                Status.IN_PROGRESS,
                0,
                0,
                40,
                LocalDateTime.now().plusHours(1))));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(), Status.IN_PROGRESS);
    }
}
