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
    int id = 0;

    public void createSubTask(classes.Subtask subtask){
        subtask.setId(id);
        epics.get(subtask.getEpicId()).addSubtasks(subtask.getId());
        subtasks.put(id, subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
        id++;
    }

    public void createTask(Task task){
        task.setId(id);
        tasks.put(id, task);
        id++;
    }

    public void createEpic(Epic epic) {
        epic.setId(id);
        epics.put(id, epic);
        id++;
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
            epic.setSubtasks(null);
            updateEpicStatus(epic);
        }
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtasks(Subtask subtask) {
        epics.get(subtasks.get(subtask.getId()).getEpicId()).addSubtasks(subtask.getId());
        updateEpic(epics.get(subtasks.get(subtask.getId()). getEpicId()));
        subtasks.put(subtask.getId(), subtask);
    }

    public ArrayList<Subtask> getSubtasksInEpic(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();

        for (int id : epics.get(epicId).getSubtasks()) {
            subtasksInEpic.add(subtasks.get(id));
        }

        return subtasksInEpic;
    }

    public void deleteEpicById(int epicId) {
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
        epics.get(subtasks.get(subID).getEpicId()).getSubtasks().remove(subID);
        updateEpic(epics.get(subtasks.get(subID).getEpicId()));
        subtasks.remove(subID);
    }

    public void updateEpicStatus(Epic epic) {
        int doneCount = 0;
        int inProgressCount = 0;
        ArrayList<Subtask> subtasksForUpdate = new ArrayList<>();

        for (int id : epic.getSubtasks()) {
            subtasksForUpdate.add(subtasks.get(id));
        }

        for (classes.Subtask sub : subtasksForUpdate) {
            switch ((sub.getStatus())) {
                case NEW:
                    break;
                case IN_PROGRESS:
                    inProgressCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
            }
        }

        if ((doneCount == subtasks.size()) && (!subtasks.isEmpty())) {
            epic.setStatus(Status.DONE);
        } else if(inProgressCount > 0 || doneCount > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        }else {
            epic.setStatus(Status.NEW);
        }
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }


}
