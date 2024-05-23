package managers.task;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;



public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
        private static FileBackedTaskManager manager;
        private static File file;

        private FileBackedTaskManagerTest() {
            super(manager);
        }
        @BeforeAll
        public static void beforeAll() throws IOException {
                file = File.createTempFile("test", ".txt");
                manager = FileBackedTaskManager.loadFromFile(file);
        }


        @Test
        public void loadFromFileTest() {
                manager.loadFromFile(file);
                Assertions.assertEquals(manager.getTasks().size(), 1);
        }
}


