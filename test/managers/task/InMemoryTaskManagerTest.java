package managers.task;

import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import org.junit.jupiter.api.*;

class InMemoryTaskManagerTest {
    private final static InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    static Epic epic;



    @BeforeEach
    public void beforeEachl() {
        epic = new Epic("первый  эпик", "описание одинепик", 0, Status.NEW);
        taskManager.createEpic(new Epic("первый  эпик", "описание одинепик", null, Status.NEW));
        taskManager.createSubTask(new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 1, 0));
        taskManager.createSubTask(new Subtask("второй саб_епик2", "описание двасаб", Status.NEW, 2, 0));
        taskManager.createTask(new Task("первая задача", "описание один", 0, Status.NEW));
        taskManager.createEpic(new Epic("второй эпик", "описание дыаэпик", 0, Status.NEW));
    }

    @AfterEach
    public void afterEach() {
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
        taskManager.resetId();
    }

    @Test
    public void updateEpicStatus_epicStatusWith2NewSubtasks() {
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.NEW,
                "Нарушена зависимость статуса Эпика от сабтасков.");
    }

    @Test
    public void updateSubtasks_updateSubtaskStatusToInProgress() {
        taskManager.updateSubtasks(new Subtask("первый  саб", "описание одинсаб",
                Status.IN_PROGRESS,
                1,
                0));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.IN_PROGRESS,
                "Нарушена зависимость статуса Эпика от сабтасков.");
    }

    @Test
    public void deleteSubtaskById_deleteOneSubtask() {
        int initialSubSize = taskManager.getSubtasks().size();
        taskManager.deleteSubtaskById(1);
        Assertions.assertEquals(initialSubSize - 1,
                taskManager.getSubtasks().size(),
                "Сабтаск в списке менеджера не был удален.");
        Assertions.assertEquals(1,
                taskManager.getEpicSubtasks(0).size(),
                "Сабтаск в списке эпика не был удален.");
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.NEW,
                "Нарушена зависимость статуса Эпика от сабтасков.");
    }

    @Test
    public void deleteAllSubtasks() {
        Subtask subtask = new Subtask("первый-удаленный саб_епик1",
                "описание одинсаб",
                Status.IN_PROGRESS,
                0,
                0);
        taskManager.createSubTask(subtask);
        taskManager.deleteAllSubtasks();
        Assertions.assertEquals(0,
                taskManager.getSubtasks().size(),
                "Сабтаски в таск менеджер не удалились.");
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.NEW,
                "Нарушена зависимость статуса Эпика от сабтасков.");

    }

    @Test
    public void deleteAllEpics() {
        taskManager.deleteAllEpics();
        Assertions.assertEquals(0,
                taskManager.getEpics().size(),
                "Эпики в таск менеджер не удалились");
        Assertions.assertEquals(0,
                taskManager.getSubtasks().size(),
                "Сабтаски в таск менеджер не удалились");
    }

    @Test
    public void deleteTaskById() {
        Assertions.assertEquals(1,
                taskManager.getTasks().size(),
                "Список задач пуст.");
        taskManager.deleteTaskById(3);
        Assertions.assertEquals(0,
                taskManager.getTasks().size(),
                "Список задач не пуст.");
    }

    @Test
    public void createSubtask_createSubtaskWithInvalidEpicId() {
        int initialSubtasksSize = taskManager.getSubtasks().size();
        taskManager.createSubTask(new Subtask("subtest", "subtest", Status.NEW, 0, 1));
        Assertions.assertEquals(taskManager.getSubtasks().size(),
                initialSubtasksSize,
                "Сабтаск добавился в список сабтасков.");
    }


    @Test
    public void getTaskById_compareReturndTasks() {
        Assertions.assertEquals(taskManager.getTaskById(3),
                taskManager.getTaskById(3),
                "Возвращенны разные объекты.");
    }

    @Test
    public  void getEpicById_compareReturnValue() {
        Assertions.assertEquals(taskManager.getEpicById(0), taskManager.getEpicById(0), "Эпики не равны.");
        Assertions.assertNotEquals(taskManager.getEpicById(4), taskManager.getEpicById(0), "Эпики равны");
    }
}