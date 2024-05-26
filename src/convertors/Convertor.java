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

    public static String convertStringToTaskType(String line) {
        HashMap<TaskType, Task> map = new HashMap<>();
        if (line.equals("id,type,name,status,description,duration,startTime,epic")) {
            return  null;
        } else if (line.isEmpty() || line.isBlank()) {
            return null;
        }
        String[] str = line.split(",");
        if (str[1].equals("TASK")) {
            return "TASK";
        } else if (str[1].equals("EPIC")) {
            return "EPIC";
        } else {
            return "SUBTASK";
        }
    }

    public static Task lineToTask(String line) {
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
         return new Task(str[2],
                str[4],
                taskId,
                Status.valueOf(str[3]),
                Integer.parseInt(str[5]),
                LocalDateTime.parse(str[6]));
    }

    public static Epic lineToEpic(String line) {
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
        epic.setDuration(Duration.ofMinutes(Integer.parseInt(str[5])));
        epic.setStartTime(LocalDateTime.parse(str[6]));
        return epic;
    }

    public static Subtask lineToSubtask(String line) {
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        Integer epicId = Integer.parseInt(str[7]);
        return new Subtask(str[2],
                str[4],
                Status.valueOf(str[3]),
                taskId,
                epicId,
                Integer.parseInt(str[5]),
                LocalDateTime.parse(str[6]));
    }
}
