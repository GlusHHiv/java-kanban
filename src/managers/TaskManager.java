package managers;

import classes.Epic;
import classes.Status;
import classes.Subtask;
import classes.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, classes.Subtask> subtasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static int id = 0;

    public void createSubTask(Subtask subtask){
        subtask.setId(getId());
        epics.get(subtask.getEpicId()).addSubtasks(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));

    }

    public void createTask(Task task){
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.deleteSubTasks();
            updateEpicStatus(epic);
        }
    }

    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }
        epics.put(epic.getId(), epic);
    }

    public void updateSubtasks(Subtask subtask) {
        if (!epics.get(subtasks.get(subtask.getId()).getEpicId()).getSubtasks().contains(subtask.getId())) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epics.get(subtasks.get(subtask.getId()).getEpicId()));
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();

        for (int id : epics.get(epicId).getSubtasks()) {
            subtasksInEpic.add(subtasks.get(id));
        }

        return subtasksInEpic;
    }

    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)) {
            return;
        }
        for (int id: epics.get(epicId).getSubtasks()) {
            subtasks.remove(id);
        }
        epics.remove(epicId);
    }

    public void deleteTaskById(int taskId) {
        if(!tasks.containsKey(taskId)) {
            return;
        }
        tasks.remove(taskId);
    }

    public void deleteSubById(int subID) {
        if(!subtasks.containsKey(subID)) {
            return;
        }
        epics.get(subtasks.get(subID).getEpicId()).getSubtasks().remove(subID);
        updateEpicStatus(epics.get(subtasks.get(subID).getEpicId()));
        subtasks.remove(subID);
    }

    private void updateEpicStatus(Epic epic) {
        int doneCount = 0;
        int newCount = 0;

        for (Subtask sub : getEpicSubtasks(epic.getId())) {
            switch ((sub.getStatus())) {
                case NEW:
                    newCount++;
                    break;
                case IN_PROGRESS:
                    break;
                case DONE:
                    doneCount++;
                    break;
            }
        }

        if (subtasks.size() == 0 || newCount == subtasks.size()) {
            epic.setStatus(Status.NEW);
            return;
        }
        if(doneCount == subtasks.size()) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskArrayList.add(task);
        }
        return taskArrayList;
    }

    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        for (Subtask sub : subtasks.values()) {
            subtaskArrayList.add(sub);
        }
        return subtaskArrayList;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicArrayList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicArrayList.add(epic);
        }
        return epicArrayList;
    }

    private int getId() {
        return id++;
    }

}
