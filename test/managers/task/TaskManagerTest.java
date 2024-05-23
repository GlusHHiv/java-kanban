package managers.task;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public abstract class TaskManagerTest<T extends TaskManager> {

    private final T manager;
    protected static LocalDateTime now = LocalDateTime.now();
    protected Integer taskId;
    protected Integer epicId;
    protected Integer subId;


    public TaskManagerTest(T manager) {
        this.manager = manager;
    }


    @BeforeEach
    public void beforeEach() {
        manager.deleteAllTasks();
        manager.deleteAllSubtasks();
        manager.deleteAllEpics();
        manager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        epicId = manager.findTaskIdByName("Test epic");
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
                now.plusHours(1)));
        taskId = manager.findTaskIdByName("Test task");
        subId = manager.findTaskIdByName("Test Sub");
    }

    @Test
    void createSubtaskAndEpicTest() {
        Assertions.assertFalse(manager.getSubtasks().isEmpty());
        Assertions.assertFalse(manager.getEpics().isEmpty());
    }

    @Test
    public void createTaskTest() {
        Assertions.assertFalse(manager.getTasks().isEmpty());
    }

    @Test
    public void updateTaskTest() {
        Assertions.assertNotNull(manager.updateTask(new Task("Updated Test task",
                "Updated test description",
                taskId,
                Status.NEW,
                40,
                now.plusHours(1))));
        Assertions.assertEquals(manager.getTaskById(taskId).getName(), "Updated Test task");
    }

    @Test
    public void updateEpicTest() {
        Epic epic = new Epic("Updated Epic",
                "Updated describtion",
                epicId,
                Status.NEW);
        Assertions.assertNotNull(manager.updateEpic(epic));
        Assertions.assertEquals(manager.getEpicById(epicId).getName(), "Updated Epic");
    }

    @Test
    public void updateSubtaskTest() {
        Assertions.assertNotNull(manager.updateSubtasks(new Subtask("First updated sub",
                "Updated Sub",
                Status.IN_PROGRESS,
                subId,
                epicId,
                20,
                now)));
        Assertions.assertEquals(manager.getSubtaskById(subId).getName(), "First updated sub");
    }
    @Test
    public void getEpicSubtasksTest() {
        Assertions.assertEquals(manager.getEpicSubtasks(epicId).size(), 1);
    }

    @Test
    public void getTasksTest() {
        Assertions.assertEquals(manager.getTasks().size(), 1);
    }

    @Test
    public void getEpicsTest() {
        Assertions.assertEquals(manager.getEpics().size(), 1);
    }

    @Test
    public void getSubtasksTest() {
        Assertions.assertEquals(manager.getSubtasks().size(), 1);
    }

    @Test
    public void getEpicByIdTest() {
        Assertions.assertNotNull(manager.getEpicById(epicId));
    }

    @Test
    public void getTaskByIdTest() {
        Assertions.assertNotNull(manager.getTaskById(taskId));
    }

    @Test
    public void getSubtaskByIdTest() {
        //System.out.println(manager.getSubtasks());
        Assertions.assertNotNull(manager.getSubtaskById(subId));
    }

    @Test
    public void getHistoryFromTaskManagerTest() {
        Assertions.assertNotNull(manager.getHistoryFromTaskManager());
    }

    @Test
    public void getPrioritizedTasks() {
        Assertions.assertEquals(manager.getPrioritizedTasks().first().getName(), manager.getEpicById(epicId).getName());
    }

    @Test
    public void deleteTaskByIDTest() {
        manager.deleteTaskById(taskId);
        Assertions.assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void deleteSubTaskByIdTest() {
        manager.deleteSubtaskById(subId);
        Assertions.assertEquals(0, manager.getSubtasks().size());
    }

    @Test
    public void deleteEpicByIdTest() {
        manager.deleteEpicById(epicId);
        Assertions.assertEquals(0, manager.getEpics().size());
    }

}
