package managers.task;

import managers.history.HistoryManager;
import model.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, model.Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected  int id = 0;
    protected static HistoryManager historyManager;
    protected TreeSet<Task> taskTreeSet = new TreeSet<>((Task task1, Task task2) -> task1.getStartTime().compareTo(task2.getStartTime()));


    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }


    @Override
    public Subtask createSubTask(Subtask subtask) {
        subtask.setId(getId());
        if (!isEpicExist(subtask.getEpicId())) { // Проверка на наличие эпика с id: epicId
            return null;
        }
        if (isIntersection(subtask)) {
            return null;
        }
        subtasks.put(subtask.getId(), subtask);
        taskTreeSet.add(subtask);
        addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
        updateEpicStatus(epics.get(subtask.getEpicId()));
        calculateEpicStartTimeAndEndTime(epics.get(subtask.getEpicId()));
        updateEpicDuration(epics.get(subtask.getEpicId()));
        addToTreeSet(epics.get(subtask.getEpicId()));
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        task.calculateEndTime();
        for (Task task1 : tasks.values()) {
            if (task1.equals(task)) {
                return null;
            }
        }
        if (isIntersection(task)) {
            return null;
        }
        task.setId(getId());
        tasks.put(task.getId(), task);
        addToTreeSet(task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        for (Epic epic1 : epics.values()) {
            if (epic1.equals(epic)) {
                return null;
            }
        }
        epic.setId(getId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        deleteFromTreeSetByTaskType(TaskType.TASK);
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        deleteFromTreeSetByTaskType(TaskType.EPIC);
        deleteAllSubtasks();
        epics.clear();
        subtasks.clear();

    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask sub : subtasks.values()) {
            historyManager.remove(sub.getId());
        }
        deleteFromTreeSetByTaskType(TaskType.SUBTASK);
        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.deleteSubTasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public Task updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return null;
        }
        taskTreeSet.remove(tasks.get(task.getId()));
        if (isIntersection(task)) {
            taskTreeSet.add(tasks.get(task.getId()));
            return null;
        }
        tasks.put(task.getId(), task);
        taskTreeSet.add(task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return null;
        }
        epic.setSubtasks(epics.get(epic.getId()).getSubtasks());
        if (!epics.get(epic.getId()).getStatus().equals(epic.getStatus())) {
            return null;
        }
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask updateSubtasks(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId()) ||
                !epics.containsKey(subtask.getEpicId()) ||
                !epics.get(subtask.getEpicId()).getSubtasks().contains(subtask.getId())) {
            return null;
        }
        taskTreeSet.remove(subtask);
        if (isIntersection(subtask)) {
            taskTreeSet.add(subtasks.get(subtask.getId()));
            return null;
        }
        subtasks.put(subtask.getId(), subtask);
        addToTreeSet(subtask);
        updateEpicStatus(epics.get(subtasks.get(subtask.getId()).getEpicId()));
        updateEpicDuration(epics.get(subtask.getEpicId()));
        calculateEpicStartTimeAndEndTime(epics.get(subtask.getEpicId()));
        return subtask;
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();

        epics.get(epicId).getSubtasks().stream()
                .peek(id -> subtasksInEpic.add(subtasks.get(id)))
                .collect(Collectors.toList());

        return subtasksInEpic;
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)) {
            return;
        }
        epics.get(epicId).getSubtasks().stream()
                        .peek(id -> taskTreeSet.remove(subtasks.get(id)))
                        .peek(id -> subtasks.remove(id))
                        .collect(Collectors.toList());
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (!tasks.containsKey(taskId)) {
            return;
        }
        taskTreeSet.remove(tasks.get(taskId));
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteSubtaskById(Integer subId) {
        if (!subtasks.containsKey(subId)) {
            return;
        }
        epics.get(subtasks.get(subId).getEpicId()).getSubtasks().remove(subId);
        updateEpicStatus(epics.get(subtasks.get(subId).getEpicId()));
        taskTreeSet.remove(subtasks.get(subId));
        subtasks.remove(subId);
        historyManager.remove(subId);
    }

    protected void updateEpicStatus(Epic epic) {
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
        if (doneCount == subtasks.size()) {
            epic.setStatus(Status.DONE);
            return;
        }
        if (inProgressCount > 0 || doneCount > 0) {
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
        historyManager.add((Task) epics.get(id));
        if (!epics.containsKey(id)) {
            return null;
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (!subtasks.containsKey(id)) {
            return null;
        }
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);

    }

    @Override
    public Task getTaskById(int id) {
        if (!tasks.containsKey(id)) {
            return null;
        }
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    protected  void addSubtaskToEpic(int epicId, int subId) {
        if (!(subtasks.containsKey(subId))) {
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

    protected void resetId() {
        id = 0;
    }

    private void updateEpicDuration(Epic epic) {
        epic.addMinutes(Duration.between(epic.getStartTime(), epic.getEndTime()).toMinutes());
    }

    private void calculateEpicStartTimeAndEndTime(Epic epic) {
        ArrayList<Subtask> sorted = getEpicSubtasks(epic.getId());
        sorted.sort((Subtask sub1, Subtask sub2) -> sub1.getStartTime().compareTo(sub2.getStartTime()));
        epic.setStartTime(sorted.get(0).getStartTime());
        sorted.sort((Subtask sub1, Subtask sub2) -> -sub1.getStartTime().compareTo(sub2.getStartTime()));
        epic.setEndTime(sorted.get(0).getEndTime());
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return taskTreeSet;
    }

    private boolean isIntersection(Task task) {
        return taskTreeSet.stream()
                .anyMatch(task1 -> ((task.getStartTime().isAfter(task1.getStartTime())
                        && task.getStartTime().isAfter(task1.getEndTime()))
                        ||(task1.getStartTime().isAfter(task.getStartTime())
                        && task1.getStartTime().isAfter(task.getEndTime()))
                        || task.getStartTime().equals(task1.getStartTime())));
    }

    protected void addToTreeSet(Task task) {
        taskTreeSet.add(task);
    }

    @Override
    public Integer findTaskIdByName(String name) {
        ArrayList<Integer> taskId = new ArrayList<>();
        taskTreeSet.stream().peek(task -> {
            if (task.getName().equals(name)) {
                taskId.add(task.getId());
            }
        }).collect(Collectors.toList());
        if (taskId.isEmpty()) {
            return null;
        }
        return taskId.get(0);
    }

    private void deleteFromTreeSetByTaskType(TaskType type) {
        List<Task> newTaskTreeSet = new ArrayList<>();
        newTaskTreeSet = taskTreeSet.stream().filter(task -> type.equals(task.getType())).collect(Collectors.toList());
        taskTreeSet.clear();
        taskTreeSet.addAll(newTaskTreeSet);
    }

}
