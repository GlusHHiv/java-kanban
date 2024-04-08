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
    }
}
