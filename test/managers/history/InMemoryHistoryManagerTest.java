package managers.history;

import static org.junit.jupiter.api.Assertions.*;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    private final Task task = new Task("Test addNewTask", "Test addNewTask description", 0, Status.NEW, 30, LocalDateTime.now());

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
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 1, LocalDateTime.now().plusHours(2)));
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
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
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 13, LocalDateTime.now().plusHours(2)));
        final List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(innitialHistorySize + 2, historyManager.getHistory().size());
    }

    @Test
    public void removeTest() {
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 13, LocalDateTime.now().plusHours(2)));
        Assertions.assertTrue(historyManager.remove(1));
    }

    @Test
    public void removeLastElementTest() {
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 13, LocalDateTime.now().plusHours(2)));
        historyManager.add(new Task("Test 4 addNewTask", "Test 2 addNewTask description", 2, Status.NEW, 13, LocalDateTime.now().plusHours(4)));
        historyManager.add(new Task("Test 3 addNewTask", "Test 2 addNewTask description", 3, Status.NEW, 13, LocalDateTime.now().plusHours(6)));
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.remove(historyManager.getLast().getId());
        Assertions.assertEquals(historyManager.getHistory().size(), innitialHistorySize - 1);
        Assertions.assertEquals(historyManager.getLast(), new Task("Test 4 addNewTask", "Test 2 addNewTask description", 2, Status.NEW, 13, LocalDateTime.now().plusHours(4)));
    }

    @Test
    public void removeMiddleElementTest() {
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 13, LocalDateTime.now().plusHours(2)));
        historyManager.add(new Task("Test 4 addNewTask", "Test 2 addNewTask description", 2, Status.NEW, 13, LocalDateTime.now().plusHours(4)));
        historyManager.add(new Task("Test 3 addNewTask", "Test 2 addNewTask description", 3, Status.NEW, 13, LocalDateTime.now().plusHours(6)));
        int innitialHistorySize = historyManager.getHistory().size();
        historyManager.remove(2);
        Assertions.assertEquals(historyManager.getHistory().size(), innitialHistorySize - 1);
    }

    @Test
    public void addFirst() {
        historyManager.add(new Task("Test 2 addNewTask", "Test 2 addNewTask description", 1, Status.NEW, 13, LocalDateTime.now().plusHours(2)));
        historyManager.add(new Task("Test 3 addNewTask", "Test 2 addNewTask description", 4, Status.NEW, 13, LocalDateTime.now().plusHours(10)));
        historyManager.addFirst(new Task("Test first addNewTask", "Test 2 addNewTask description", 12, Status.NEW, 13, LocalDateTime.now().plusHours(4)));
        Assertions.assertEquals(historyManager.getFisrt(), new Task("Test first addNewTask", "Test 2 addNewTask description", 12, Status.NEW, 13, LocalDateTime.now().plusHours(4)));
    }

    @Test
    public void removeFirstTest() {
        historyManager.add(new Task("Test 2 addNewTask",
                "Test 2 addNewTask description",
                1,
                Status.NEW,
                13,
                LocalDateTime.now().plusHours(2)));
        historyManager.add(new Task("Test 3 addNewTask",
                "Test 2 addNewTask description",
                4,
                Status.NEW,
                13,
                LocalDateTime.now().plusHours(10)));
        historyManager.remove(historyManager.getFisrt().getId());
        Assertions.assertEquals(historyManager.getFisrt(), new Task("Test 3 addNewTask",
                "Test 2 addNewTask description",
                4,
                Status.NEW,
                13,
                LocalDateTime.now().plusHours(10)));
    }
}

