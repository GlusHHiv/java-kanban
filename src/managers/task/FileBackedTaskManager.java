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


    private FileBackedTaskManager(File file, HistoryManager historyManager) {
        super(historyManager);
        this.file = file;
            fillData();
    }

    private void fillData() {
        historyManager = new InMemoryHistoryManager();
        try (BufferedReader buffer = new BufferedReader(new FileReader(file,
                Charset.defaultCharset()))) {
                String line = buffer.readLine();
                boolean isPreviosEmpty = false;
                while (buffer.ready()) {
                    line = buffer.readLine();
                    if (Convertor.convertStringToTaskType(line) == null) {
                        break;
                    }
                    if (isPreviosEmpty) {
                        ArrayList<Integer> historyIds = Convertor.convertHistoryFromString(line);
                        for (Integer i: historyIds) {
                            if (subtasks.containsKey(i)) {
                                historyManager.add(subtasks.get(i));
                            } else if (epics.containsKey(i)) {
                                historyManager.add(epics.get(i));
                            } else if (tasks.containsKey(i)) {
                                historyManager.add(tasks.get(i));
                            }
                        }

                    } else if (line.isEmpty()) {
                        isPreviosEmpty = true;
                    }
                    if (Convertor.convertStringToTaskType(line).equals("SUBTASK")) {
                        Subtask subtask = (Subtask) Convertor.lineToSubtask(line);
                        subtasks.put(subtask.getId(), subtask);
                        addToTreeSet(subtask);
                        addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
                    } else if (Convertor.convertStringToTaskType(line).equals("EPIC")) {
                        Epic epic = (Epic) Convertor.lineToEpic(line);
                        epics.put(epic.getId(), epic);
                        addToTreeSet(epic);
                    } else if (Convertor.convertStringToTaskType(line).equals("TASK")) {
                        Task task = Convertor.lineToTask(line);
                        tasks.put(task.getId(), task);
                        addToTreeSet(task);
                    }

                }
                if (!getAllIds().isEmpty()) {
                    id = getAllIds().get(getAllIds().size() - 1) + 1;
                }
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка чтения");
            }


    }



    private  void save() {
        if (file.equals(null)) {
            return;
        }
        try {
            try (Writer writer = new FileWriter(file, Charset.defaultCharset())) {
                writer.write("id,type,name,status,description,duration,startTime,epic\n");
                ArrayList<Integer> ids = getAllIds(); //
                if (ids.isEmpty()) {
                    return;
                }
                for (Integer i : ids) { // запись в файл задач по id по порядку возрастания
                    if (subtasks.containsKey(i)) {
                        writer.write(Convertor.convertSubtaskToString(subtasks.get(i)));
                    } else if (epics.containsKey(i)) {
                        writer.write(Convertor.convertEpicToString(epics.get(i)));
                    } else if (tasks.containsKey(i)) {
                        writer.write(Convertor.convertTaskToString(tasks.get(i)));
                    }
                }
                if (!historyManager.getHistory().isEmpty()) {
                    writer.write("\n");
                    ArrayList<Integer> historyIds = Convertor.
                            convertHistoryToString(getHistoryFromTaskManager().toString());
                    writer.write(historyIds.toString());
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
        super.updateSubtasks(subtask);
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
        save();

        return epic;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }
}
