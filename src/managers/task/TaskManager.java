package managers.task;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    Subtask createSubTask(Subtask subtask);

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtasks(Subtask subtask);

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

    List<Task> getHistoryFromTaskManager();
}
