import managers.history.InMemoryHistoryManager;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        manager.createEpic(new Epic("Test epic",
                "Test",
                0,
                Status.NEW));
        manager.createSubTask((new Subtask("Test Sub",
                "Test",
                Status.NEW,
                0,
                0,
                20,
                LocalDateTime.now())));
        manager.createSubTask((new Subtask("Test Sub 2",
                "Test",
                Status.NEW,
                0,
                0,
                20,
                LocalDateTime.now().plusHours(1))));
        System.out.println(manager.getEpicById(0).getEndTime().equals(manager.getSubtaskById(2).getEndTime()));
    }
}
