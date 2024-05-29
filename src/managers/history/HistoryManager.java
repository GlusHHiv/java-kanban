package managers.history;
import model.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    boolean remove(int id);

    void addFirst(Task task);

    Task getFisrt();

    Task getLast(); }
