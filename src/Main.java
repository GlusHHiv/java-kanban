import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import managers.task.FileBackedTaskManager;
import managers.task.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager( new File("C:\\Users\\polia\\dev\\Educational\\Java-test.txt"), new InMemoryHistoryManager());
        //manager = manager.loadFromFile(new File("C:\\Users\\polia\\dev\\Educational\\Java-test.txt"));
        manager.createTask(new Task("sss задача", "описанвывые один", 0, Status.NEW));
        manager.createEpic(new Epic("ssss эпик", "описание одинепик", 0, Status.NEW));
        //manager.createSubTask(new Subtask("aaa саб_епик1", "описание одинсаб", Status.NEW, 0, 1));
        manager.createTask(new Task("sa задача", "описанвывые один", 0, Status.NEW));
        manager.createTask(new Task("ssa задача", "описанвывые один", 0, Status.NEW));
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
    }
}
