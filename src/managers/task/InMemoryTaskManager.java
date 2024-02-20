package managers.task;

import managers.history.HistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, model.Subtask> subtasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static int id = 0;
    private static HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }
    @Override
    public void createSubTask(Subtask subtask){
        subtask.setId(getId());
        if(!isEpicExist(subtask.getEpicId())) { // Проверка на наличие эпика с id: epicId
            tasks.put(subtask.getId(), new Task (subtask.getName(),
                    subtask.getDescription(),
                    subtask.getId(),
                    subtask.getStatus()));
                    return;
        }
        subtasks.put(subtask.getId(), subtask);
        addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
        updateEpicStatus(epics.get(subtask.getEpicId()));


    }

    @Override
    public void createTask(Task task){
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.deleteSubTasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtasks(Subtask subtask) {
        if (!epics.get(subtasks.get(subtask.getId()).getEpicId()).getSubtasks().contains(subtask.getId())) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epics.get(subtasks.get(subtask.getId()).getEpicId()));
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();

        for (int id : epics.get(epicId).getSubtasks()) {
            subtasksInEpic.add(subtasks.get(id));
        }

        return subtasksInEpic;
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)) {
            return;
        }
        for (int id: epics.get(epicId).getSubtasks()) {
            subtasks.remove(id);
        }
        epics.remove(epicId);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if(!tasks.containsKey(taskId)) {
            return;
        }
        tasks.remove(taskId);
    }

    @Override
    public void deleteSubtaskById(Integer subId) {
        if(!subtasks.containsKey(subId)) {
            return;
        }
        epics.get(subtasks.get(subId).getEpicId()).getSubtasks().remove(subId);
        updateEpicStatus(epics.get(subtasks.get(subId).getEpicId()));
        subtasks.remove(subId);

    }

    private void updateEpicStatus(Epic epic) {
        int doneCount = 0;
        int inProgressCount = 0;

        for (Subtask sub : getEpicSubtasks(epic.getId())) {
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

        if (subtasks.size() == 0) {
            epic.setStatus(Status.NEW);
            return;
        }
        if(doneCount == subtasks.size()) {
            epic.setStatus(Status.DONE);
            return;
        }
        if (inProgressCount > 0) {
            epic.setStatus(Status.IN_PROGRESS);
            return;
        }
        epic.setStatus(Status.NEW);
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskArrayList.add(task);
        }
        return taskArrayList;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        for (Subtask sub : subtasks.values()) {
            subtaskArrayList.add(sub);
        }
        return subtaskArrayList;
    }

    @Override
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

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);

    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    private void addSubtaskToEpic(int epicId, int subId) {
        if(!(subtasks.containsKey(subId))){
            return;
        }
        epics.get(epicId).addSubtasks(subId);
    }

    private boolean isEpicExist(Integer id) {
        if (!epics.containsKey(id)) {
            return false;
        }
        return true;
    }

    @Override
    public List<Task> getHistoryFromTaskManager() {
        return historyManager.getHistory();
    }





}
