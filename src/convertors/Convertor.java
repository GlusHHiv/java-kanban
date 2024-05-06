package convertors;

import model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Convertor {

    public static String convertTaskToString(Task task) {
        return task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getDuration().toMinutes() + "," +
                task.getStartTime() + "\n";
    }

    public static String convertEpicToString(Epic task) {
        return  task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getDuration().toMinutes() + "," +
                task.getStartTime() + "\n";
    }

    public static String convertSubtaskToString(Subtask task) {
        return  task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getDuration().toMinutes() + "," +
                task.getStartTime() + "," +
                task.getEpicId() + "\n";
    }


    public static ArrayList<Integer> convertHistoryFromString(String line) {
        line = line
                .replace("]", "")
                .replace("[", "")
                .replace(" ", "");
        String[] str = line.split(",");
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            ids.add(Integer.parseInt(str[i]));
        }
        return ids;
    }

    public static HashMap<TaskType, Task> convertStringToTask(String line) {
        if (line.equals("id,type,name,status,description,epic")) {
            return  null;
        } else if (line.isEmpty() || line.isBlank()) {
            return null;
        }
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2],
                    str[4],
                    taskId,
                    Status.valueOf(str[3]),
                    Integer.parseInt(str[5]),
                    LocalDateTime.parse(str[6]));
            HashMap<TaskType, Task> map = new HashMap<>();
            map.put(TaskType.TASK, task);
            return map;
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            epic.setDuration(Duration.ofMinutes(Integer.parseInt(str[5])));
            epic.setStartTime(LocalDateTime.parse(str[6]));
            HashMap<TaskType, Task> map = new HashMap<>();
            map.put(TaskType.EPIC, epic);
            return map;
        } else {
            Integer epicId = Integer.parseInt(str[7]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId, Integer.parseInt(str[5]), LocalDateTime.parse(str[6]));
            HashMap<TaskType, Task> map = new HashMap<>();
            map.put(TaskType.SUBTASK, subtask);
            return map;
        }
    }
}
