package managers.history;

import model.Task;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import model.LinkedMapList;

public class InMemoryHistoryManager implements HistoryManager {
    private  final static LinkedMapList<Task> history = new LinkedMapList<>();
    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        if (history.contains(task.getId())) {
            history.removeElement(task.getId());
        }
        history.linkLast(task, task.getId());
    }

    @Override
    public void remove(int id) {
        if (!history.contains(id)) {
            return;
        }
        history.remove(id);
    }

}

