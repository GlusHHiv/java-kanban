package managers;

import classes.Epic;
import classes.Status;
import classes.Subtask;
import classes.Task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private final static Managers managers = new Managers();


    @BeforeAll
    public static void beforeAll() {
        final Epic epic = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);
        managers.getDefault().createEpic(epic);
        Subtask subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 0, 0);
        Subtask subtask1 = new Subtask("второй саб_епик2", "описание двасаб", Status.NEW, 0, 0);
        managers.getDefault().createSubTask(subtask);
        managers.getDefault().createSubTask(subtask1);
        managers.getDefault().createTask(new Task("первая задача", "описание один", 0, Status.NEW));


    }

    @Test
    public void epicStatus() {
        Assertions.assertEquals(managers.getDefault().getEpicById(0).getStatus(), Status.NEW);
        managers.getDefault().updateSubtasks(new Subtask("первый изм саб_епик1", "описание одинсаб",
                Status.IN_PROGRESS, 1, 0));
        Assertions.assertEquals(managers.getDefault().getEpicById(0).getStatus(), Status.IN_PROGRESS);

    }

    @Test
    public void deleteSubtask() {
        managers.getDefault().deleteSubtaskById(1);
        Assertions.assertEquals(1, managers.getDefault().getSubtasks().size());
        Assertions.assertEquals(1, managers.getDefault().getEpicSubtasks(0).size());
        Assertions.assertEquals(managers.getDefault().getEpicById(0).getStatus(), Status.NEW);
    }

    @Test
    public void deleteAllSubtasks() {
        Subtask subtask = new Subtask("первый-удаленный саб_епик1",
                "описание одинсаб",
                Status.IN_PROGRESS,
                0,
                0);
        managers.getDefault().createSubTask(subtask);
        managers.getDefault().deleteAllSubtasks();
        Assertions.assertEquals(0, managers.getDefault().getSubtasks().size());
        Assertions.assertEquals(managers.getDefault().getEpicById(0).getStatus(), Status.NEW);

    }

    @Test
    public void deleteEpic() {
        managers.getDefault().deleteAllEpics();
        Assertions.assertEquals(0, managers.getDefault().getEpics().size());
        Assertions.assertEquals(0, managers.getDefault().getSubtasks().size());
    }

    @Test
    public void deleteTask() {
        Assertions.assertEquals(1, managers.getDefault().getTasks().size());
        managers.getDefault().deleteTaskById(3);
        Assertions.assertEquals(0, managers.getDefault().getTasks().size());
    }
}