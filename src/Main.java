import managers.history.InMemoryHistoryManager;
import managers.task.FileBackedTaskManager;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(new File("C:\\Users\\polia\\dev\\Educational\\Java-test.txt"));
        manager.createEpic(new Epic("первый эпик", "описание одинепик", 0, Status.NEW));
        manager.createSubTask(new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 1, 0, 20, LocalDateTime.now()));
        System.out.println(manager.getEpicById(0).getEndTime());
        System.out.println(manager.getPrioritizedTasks());
    }
}
