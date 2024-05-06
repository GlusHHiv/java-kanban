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
    public TaskManagerTest(T manager) {
        this.manager = manager;
    }


    @Test
    void createSubtaskAndEpicTest() {
        manager.createEpic(manager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW)));
        manager.createSubTask((new Subtask("Test Sub",
                "Test",
                Status.NEW,
                1,
                0,
                20,
                now)));

        Assertions.assertFalse(manager.getSubtasks().isEmpty());
        Assertions.assertFalse(manager.getEpics().isEmpty());
    }

    @Test
    public void createTaskTest() {
        manager.createTask(new Task("Test task",
                "test description",
                0,
                Status.NEW,
                40,
                now.plusHours(1)));
        Assertions.assertFalse(manager.getTasks().isEmpty());
    }

    @Test
    public void updateTaskTest() {
        Assertions.assertNotNull(manager.updateTask(new Task("Updated Test task",
                "Updated test description",
                4,
                Status.NEW,
                40,
                now.plusHours(1))));
        Assertions.assertEquals(manager.getTaskById(4).getName(), "Updated Test task");
    }

    @Test
    public void updateEpicTest() {
        Assertions.assertNotNull(manager.updateEpic(new Epic("Updated Epic",
                "Updated describtion",
                0,
                Status.IN_PROGRESS)));
        Assertions.assertEquals(manager.getEpicById(0).getName(), "Updated Epic");
    }

    @Test
    public void updateSubtaskTest() {
        Assertions.assertNotNull(manager.updateSubtasks(new Subtask("First updated sub",
                "Updated Sub",
                Status.IN_PROGRESS,
                2,
                0,
                20,
                now)));
        Assertions.assertEquals(manager.getSubtaskById(2).getName(), "First updated sub");
    }
    @Test
    public void getEpicSubtasksTest() {
        Assertions.assertEquals(manager.getEpicSubtasks(0).size(), 1);
    }

    @Test
    public void getTasksTest() {
        manager.createTask(manager.createTask(new Task("Test task2",
                "test description",
                0,
                Status.NEW,
                40,
                now.plusHours(3))));
        Assertions.assertFalse(manager.getTasks().isEmpty());
    }

    @Test
    public void getEpicsTest() {
        Assertions.assertEquals(manager.getEpics().size(), 2);
    }

    @Test
    public void getSubtasksTest() {
        Assertions.assertEquals(manager.getSubtasks().size(), 1);
    }

    @Test
    public void getEpicByIdTest() {
        Assertions.assertNotNull(manager.getEpicById(0));
    }

    @Test
    public void getTaskByIdTest() {
        Assertions.assertNotNull(manager.getTaskById(4));
    }

    @Test
    public void getSubtaskByIdTest() {
        Assertions.assertNotNull(manager.getSubtaskById(2));
    }

    @Test
    public void getHistoryFromTaskManagerTest() {
        Assertions.assertNotNull(manager.getHistoryFromTaskManager());
    }

    @Test
    public void getPrioritizedTasks() {
        Assertions.assertEquals(manager.getPrioritizedTasks().first().getName(), manager.getSubtaskById(2).getName());
    }


}
