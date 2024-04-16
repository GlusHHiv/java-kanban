package managers.task;

import convertors.Convertor;
import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import model.*;
import exceptions.ManagerSaveException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static File file;
    ArrayList<Integer> historyIds = new ArrayList<>();


    private FileBackedTaskManager(File file, HistoryManager historyManager) {
        super(historyManager);
        this.file = file;
        dataFilling();
    }

    private void dataFilling() {
        try {
            historyManager = new InMemoryHistoryManager();
            try (BufferedReader buffer = new BufferedReader(new FileReader(file,
                    Charset.defaultCharset()))) {
                String line = buffer.readLine();
                boolean isPreviosEmpty = false;
                while (buffer.ready()) {
                    line = buffer.readLine();
                    if (Convertor.convertStringToTask(line) == null) {
                        break;
                    }
                    if (isPreviosEmpty) {
                        historyManager.add(Convertor.convertHistoryFromString(line));
                    } else if (line.isEmpty()) {
                        isPreviosEmpty = true;
                    }
                    if (Convertor.convertStringToTask(line).containsKey(TaskType.SUBTASK)) {
                        Subtask subtask = (Subtask) Convertor.convertStringToTask(line).get(TaskType.SUBTASK);
                        subtasks.put(subtask.getId(), subtask);
                        addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
                    } else if (Convertor.convertStringToTask(line).containsKey(TaskType.EPIC)) {
                        Epic epic = (Epic) Convertor.convertStringToTask(line).get(TaskType.EPIC);
                        epics.put(epic.getId(), epic);
                    } else if (Convertor.convertStringToTask(line).containsKey(TaskType.TASK)) {
                        Task task = Convertor.convertStringToTask(line).get(TaskType.TASK);
                        tasks.put(task.getId(), task);
                    }

                }
                if (!getAllIds().isEmpty()) {
                    id = getAllIds().get(getAllIds().size() - 1) + 1;
                }
                save();
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка чтения");
            }
        } catch (ManagerSaveException managerSaveException) {
            managerSaveException.getMessage();
        }
    }



    private  void save() {
        if (file.equals(null)) {
            return;
        }
        try {
            try (Writer writer = new FileWriter(file, Charset.defaultCharset())) {
                writer.write("id,type,name,status,description,epic\n");
                ArrayList<Integer> ids = getAllIds(); //
                if (ids.isEmpty()) {
                    System.out.println("Мапы пусты");
                    return;
                }
                for (Integer i : ids) { // запись в файл задач по id по порядку возрастания
                    if (subtasks.containsKey(i)) {
                        if (historyManager.getHistory().contains(subtasks.get(i))){
                            historyIds.add(i);
                        }
                        writer.write(Convertor.convertSubtaskToString(subtasks.get(i)));
                    } else if (epics.containsKey(i)) {
                        if (historyManager.getHistory().contains(i)){
                            historyIds.add(i);
                        }
                        writer.write(Convertor.convertEpicToString(epics.get(i)));
                    } else if (tasks.containsKey(i)) {
                        if (historyManager.getHistory().contains(tasks.get(i))){
                            historyIds.add(i);
                        }
                        writer.write(Convertor.convertTaskToString(tasks.get(i)));
                    }
                }
                if (!historyIds.isEmpty()) {
                    System.out.println(historyIds);
                    writer.write("\n");
                    for (Integer i : historyIds) {
                        if (subtasks.containsKey(i)) {
                            writer.write(Convertor.convertSubtaskToString(subtasks.get(i)));
                        } else if (epics.containsKey(i)) {
                            writer.write(Convertor.convertEpicToString(epics.get(i)));
                        } else if (tasks.containsKey(i)) {
                            writer.write(Convertor.convertTaskToString(tasks.get(i)));
                        }
                    }
                }
            } catch (IOException exception) {
                throw new ManagerSaveException("Ошибка сохранения");//по усл ТЗ нужно отлавливать и выбрасываать
            }
        } catch (ManagerSaveException e) {
            e.getMessage();
        }
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {
        Subtask sub = super.createSubTask(subtask);
        save();
        return sub;
    }

    @Override
    public  Task createTask(Task task) {
        Task task1 = super.createTask(task);
        save();
        return task1;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic epic1 = super.createEpic(epic);
        save();
        return epic1;
    }

    public static FileBackedTaskManager loadFromFile(File file1)  {
        return new FileBackedTaskManager(file1, historyManager);
    }

    @Override
    public void  deleteAllTasks() {
        super.deleteAllTasks();
        save();

    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task updateTask(Task task) {
        super.updateTask(task);
        save();
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask updateSubtasks(Subtask subtask) {
        super.updateTask(subtask);
        save();
        return subtask;
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer subID) {
        super.deleteSubtaskById(subID);
        save();
    }


    private  ArrayList<Integer> getAllIds() {
        Comparator<Integer> personalComparator = new PersonalComparator();
        ArrayList<Integer> ids = new ArrayList<>();

        for (Integer taskId : tasks.keySet()) {
            ids.add(taskId);
        }
        for (Integer epicId : epics.keySet()) {
            ids.add(epicId);
        }
        for (Integer subId : subtasks.keySet()) {
            ids.add(subId);
        }
        ids.sort(personalComparator);
        return ids;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        historyIds.add(id);
        save();

        return epic;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        historyIds.add(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        historyIds.add(id);
        save();
        return subtask;
    }
}
