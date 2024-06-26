package managers.history;

import model.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedMapList<Task> history = new LinkedMapList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        if ((!history.isEmpty()) && (history.contains(task.getId()))) {
            history.removeElement(task.getId());
        }
        history.linkLast(task, task.getId());
    }

    @Override
    public boolean remove(int id) {
        if (!history.contains(id)) {
            return false;
        }
        history.removeElement(id);
        return true;
    }


    @Override
    public void addFirst(Task task) {
        if ((!history.isEmpty()) && (history.contains(task.getId()))) {
            history.removeElement(task.getId());
        }
        history.linkFirst(task, task.getId());
    }

    @Override
    public Task getFisrt() {
        return history.getFirst();
    }

    @Override
    public Task getLast() {
        return history.getLast();
    }
}


