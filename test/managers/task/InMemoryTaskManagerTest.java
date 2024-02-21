package managers.task;

import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private final static InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());


    @BeforeAll
    public static void beforeAll() {
        final Epic epic = new Epic("первый  эпик", "описание одинепик", 0, Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 0, 0);
        Subtask subtask1 = new Subtask("второй саб_епик2", "описание двасаб", Status.NEW, 0, 0);
        taskManager.createSubTask(subtask);
        taskManager.createSubTask(subtask1);
        taskManager.createTask(new Task("первая задача", "описание один", 0, Status.NEW));
        Epic epic1 = new Epic("второй эпик", "описание дыаэпик", 0, Status.NEW);
        taskManager.createEpic(epic1);
    }

    @Test
    public void updateEpicStatus_epicStatusWith2NewSubtasks() {
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.NEW,
                "Нарушена зависимость статуса Эпика от сабтасков.");
    }

    @Test
    public void updateSubtasks_updateSubtaskStatusToInProgress() {
        taskManager.updateSubtasks(new Subtask("первый изм саб_епик1",
                "описание одинсаб",
                Status.IN_PROGRESS,
                1,
                0));
        Assertions.assertEquals(taskManager.getEpicById(0).getStatus(),
                Status.IN_PROGRESS,
                "Нарушена зависимость статуса Эпика от сабтасков.");
    }

    @Test
    public void deleteSubtaskById_deleteOneSubtask() {
        taskManager.deleteSubtaskById(1);
        Assertions.assertEquals(1,
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
    public void createSubtask_reforamateSubtaskWithWrongEpicIdIntoTask() {
        int initialSubtasksSize = taskManager.getSubtasks().size();
        int initialTasksSize = taskManager.getTasks().size();
        taskManager.createSubTask(new Subtask("subtest", "subtest", Status.NEW, 0, 1));
        Assertions.assertEquals(taskManager.getSubtasks().size(),
                initialSubtasksSize,
                "Сабтаск добавился в список сабтасков.");
        Assertions.assertEquals(initialTasksSize + 1,
                taskManager.getTasks().size(),
                "Список Задач не пополнился.");
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