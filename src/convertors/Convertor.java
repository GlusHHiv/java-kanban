package convertors;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static ArrayList<Integer> convertHistoryToIds(List<Task> history, List<Integer> allIds) {
        ArrayList<Integer> taskIds = new ArrayList<>();
        for (Integer i : allIds) {
            if (history.contains(i)) {
                taskIds.add(i);
            }
        /*if (history.isEmpty()) {
            return null;
        }
        String historyStr  = history.toString();
        historyStr = historyStr.replace("{", "").replace("}", "");
        String[] lines = historyStr.split("\n");
        for (String s: lines) {
            //System.out.println(s);
            if (!s.isEmpty() && !s.isBlank()) {
                s = s.substring(historyStr.indexOf(", id: ") + 5);
                s = s.substring(0, 1);
                if (s.isBlank() || s.isEmpty()) {
                    break;
                }
                try {
                    taskIds.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {

                }
            }*/
            System.out.println(taskIds);
        }

        return taskIds;
    }

    public static Task convertHistoryFromString(String line) {
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

    public static HashMap<TaskType, Task> convertStringToTask(String line) {
        if (line.equals("id,type,name,status,description,epic")) {
            return  null;
        } else if (line.isEmpty() || line.isBlank()) {
            return null;
        }
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2], str[4], taskId, Status.valueOf(str[3]));
            HashMap<TaskType, Task> map = new HashMap<>();
            map.put(TaskType.TASK, task);
            return map;
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            HashMap<TaskType ,Task> map = new HashMap<>();
            map.put(TaskType.EPIC ,epic);
            return map;
        } else {
            Integer epicId = Integer.parseInt(str[5]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId);
            HashMap<TaskType ,Task> map = new HashMap<>();
            map.put(TaskType.SUBTASK ,subtask);
            return map;
        }
    }
}
