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
                manager = new FileBackedTaskManager(file, new InMemoryHistoryManager());
        }

        @Test
        public void saveTest() {
                manager.createTask(new Task("test задача", "описанвывые один", 0, Status.NEW));
                Assertions.assertEquals(manager.readFile(), "id,type,name,status,description,epic\n" +
                        "0,TASK,test задача,NEW,описанвывые один");
        }

        @Test
        public void loadFromFileTest() {
                manager.loadFromFile(file);
                Assertions.assertEquals(manager.readFile(), "id,type,name,status,description,epic\n" +
                        "0,TASK,test задача,NEW,описанвывые один");
        }
}