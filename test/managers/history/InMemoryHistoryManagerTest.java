package managers.history;

import static org.junit.jupiter.api.Assertions.*;

import managers.history.InMemoryHistoryManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    private final Task task = new Task("Test addNewTask", "Test addNewTask description", 0, Status.NEW);

    @BeforeEach
    public  void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    
    @Test
    public void add_addTaskToHistory() {
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История пустая.");
        assertEquals(innitialHistorySize + 1, history.size(), "Не один элемент добавился.");
    }

    @Test
    public void add_addTwoSameTasksToHistory() {
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.add(task);
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW));
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        System.out.println(history.get(0));
        Assertions.assertEquals(history.size(), innitialHistorySize + 2);
    }

    @Test
    public void add_addTaskToHistoryAndCompare() {
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.add(task);
        Assertions.assertEquals(task, historyManager.getHistory().get(innitialHistorySize), "Задачи не равны.");
    }

    @Test
    public void getHistory() {
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.add(task);
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW));
        final List<Task> history = historyManager.getHistory();
        System.out.println(history);
        Assertions.assertEquals(innitialHistorySize + 2, historyManager.getHistory().size());
    }
}