package managers.task;

import managers.history.InMemoryHistoryManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileBackedTaskManagerTest {
        private static FileBackedTaskManager manager;
        private static File file;

        @BeforeAll
        public static void beforeAll() throws IOException {
                file = File.createTempFile("test", ".txt");
                manager = FileBackedTaskManager.loadFromFile(file);
        }

        @Test
        public void saveTest() {
                manager.createTask(new Task("test задача", "описанвывые один", 0, Status.NEW));
                Assertions.assertEquals(manager.getTasks().size(), 1);
        }

        @Test
        public void loadFromFileTest() {
                manager.loadFromFile(file);
                Assertions.assertEquals(manager.getTasks().size(), 1);
        }
}