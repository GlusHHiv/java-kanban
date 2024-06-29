package managers;


import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;

public class Managers {
    private static TaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

    public static TaskManager getDefault() {
        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
