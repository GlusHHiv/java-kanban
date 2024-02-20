package managers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private final static InMemoryTaskManager managers = new InMemoryTaskManager(new InMemoryHistoryManager());


    @BeforeAll
    public static void beforeAll() {
        final Epic epic = new Epic("первый  эпик", "описание одинепик", 0, Status.NEW);
        managers.createEpic(epic);
        Subtask subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 0, 0);
        Subtask subtask1 = new Subtask("второй саб_епик2", "описание двасаб", Status.NEW, 0, 0);
        managers.createSubTask(subtask);
        managers.createSubTask(subtask1);
        managers.createTask(new Task("первая задача", "описание один", 0, Status.NEW));
        Epic epic1 = new Epic("второй эпик", "описание дыаэпик", 0, Status.NEW);
        managers.createEpic(epic1);
    }

    @Test
    public void shouldReturnStatusNewWithNewSubtasks() {
        Assertions.assertEquals(managers.getEpicById(0).getStatus(), Status.NEW);
    }

    @Test
    public void shouldReturnStatusIN_PROGRESSWithUpdatedSubtask() {
        managers.updateSubtasks(new Subtask("первый изм саб_епик1", "описание одинсаб",
                Status.IN_PROGRESS, 1, 0));
        Assertions.assertEquals(managers.getEpicById(0).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldDecreaseSizeOfSubtasksWhenOneDeleted() {
        managers.deleteSubtaskById(1);
        Assertions.assertEquals(1, managers.getSubtasks().size());
        Assertions.assertEquals(1, managers.getEpicSubtasks(0).size());
        Assertions.assertEquals(managers.getEpicById(0).getStatus(), Status.NEW);
    }

    @Test
    public void shouldDeleteAllSubtasksInEpic() {
        Subtask subtask = new Subtask("первый-удаленный саб_епик1",
                "описание одинсаб",
                Status.IN_PROGRESS,
                0,
                0);
        managers.createSubTask(subtask);
        managers.deleteAllSubtasks();
        Assertions.assertEquals(0, managers.getSubtasks().size());
        Assertions.assertEquals(managers.getEpicById(0).getStatus(), Status.NEW);

    }

    @Test
    public void shouldDeleteSubtasksWithEpic() {
        managers.deleteAllEpics();
        Assertions.assertEquals(0, managers.getEpics().size());
        Assertions.assertEquals(0, managers.getSubtasks().size());
    }

    @Test
    public void shouldDeleteTask() {
        Assertions.assertEquals(1, managers.getTasks().size());
        managers.deleteTaskById(3);
        Assertions.assertEquals(0, managers.getTasks().size());
    }

    @Test
    public void shouldReturnFalseWhenAddedEpicAsSubtask() {
        managers.addSubtaskToEpic(managers.getEpicById(0).getId(), 0);
        Assertions.assertFalse(managers.getEpicById(0).getSubtasks().contains(0));
    }

    @Test
    public void shouldREturnNullInEpicIdWhenSubtaskAddedAsEpic() {
        Subtask subtask = new Subtask("первый-такой саб_епик1", "описание одинсаб", Status.NEW, 0, 1);
        managers.createSubTask(subtask);
        Assertions.assertNull(managers.getSubtaskById(4).getEpicId());
    }

    @Test
    public void checkTaskId() {
        Assertions.assertEquals(managers.getTaskById(3), managers.getTaskById(3));
    }

    @Test
    public  void checkTasksHeritage() {
        System.out.println(managers.getEpics());
        Assertions.assertEquals(managers.getEpicById(0), managers.getEpicById(0), "эпики не равны");
        Assertions.assertNotEquals(managers.getEpicById(4), managers.getEpicById(0));
    }
}