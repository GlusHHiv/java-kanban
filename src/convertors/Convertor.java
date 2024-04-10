package convertors;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskTypes;

import java.util.List;

public class Convertor {
    public static String convertTaskToString(Task task) {
        return task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }

    public static String convertEpicToString(Epic task) {
        return task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }

    public static String convertSubtaskToString(Subtask task) {
        return  task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getEpicId() + "\n";
    }

    public static String convertHistory(List<Task> history) {
        String line = "";
        for (Task task : history) {
            if (task.getType().equals(TaskTypes.SUBTASK)) {
                line = String.join(line, convertSubtaskToString((Subtask) task));
            } else {
                line = String.join(line, convertTaskToString(task));
            }
        }
        return line;
    }
}
