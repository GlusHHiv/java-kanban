package convertors;

import model.*;

import java.util.List;

public class Convertor {


    public static String convertTaskToString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }

    public static String convertEpicToString(Epic task) {
        return  task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }

    public static String convertSubtaskToString(Subtask task) {
        return  task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getEpicId() + "\n";
    }

    public static String convertHistory(List<Task> history) {
        String line = "";
        for (Task task : history) {
            if (task.getType().equals(TaskType.SUBTASK)) {
                line = String.join(line, convertSubtaskToString((Subtask) task));
            } else {
                line = String.join(line, convertTaskToString(task));
            }
        }
        return line;
    }

    public static Task convertHistoryFromStringAndAdd(String line) {
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2], str[4], taskId, Status.valueOf(str[3]));
            return task;
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            return epic;
        } else {
            Integer epicId = Integer.parseInt(str[5]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId);
            return subtask;
        }
    }

    public static Task convertStringToTaskAndLoad(String line) {
        if (line.equals("id,type,name,status,description,epic")) {
            return new Task("dkd", "das", 999999999, Status.NEW);
        } else if (line.isEmpty() || line.isBlank()) {
            return new Task("dkd", "das", 999999999, Status.NEW);
        }
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2], str[4], taskId, Status.valueOf(str[3]));
            return task;
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            return epic;
        } else {
            Integer epicId = Integer.parseInt(str[5]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId);
            return subtask;
        }
    }
}
