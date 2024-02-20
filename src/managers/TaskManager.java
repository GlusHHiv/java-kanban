package managers;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createSubTask(Subtask subtask);

    void createTask(Task task);

    void createEpic(Epic epic);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtasks(Subtask subtask);

    ArrayList<Subtask> getEpicSubtasks(int epicId);

    void deleteEpicById(int epicId);

    void deleteTaskById(int taskId);

    void deleteSubtaskById(Integer subID);

    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    Task getTaskById(int id);

    List<Task> getHistory();
}
