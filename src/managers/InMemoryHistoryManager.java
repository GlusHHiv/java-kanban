package managers;

import classes.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private  final static ArrayList<Task> history = new ArrayList<>();
    @Override
    public List<Task> getHistory() {

        return history;
    }

    public void checkSizeOfHistory() {
        if(history.size() >= 10) {
            history.remove(0);
        }
    }

    @Override
    public void add(Task task) {
        if(history.size() >= 10) {
            history.remove(0);
        }
        history.add(task);
    }


}

