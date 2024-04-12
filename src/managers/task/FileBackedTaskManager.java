package managers.task;

import convertors.Convertor;
import managers.history.HistoryManager;
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
                        writer.write(Convertor.convertSubtaskToString(subtasks.get(i)));
                    } else if (epics.containsKey(i)) {
                        writer.write(Convertor.convertEpicToString(epics.get(i)));
                    } else if (tasks.containsKey(i)) {
                        writer.write(Convertor.convertTaskToString(tasks.get(i)));
                    }
                }

                writer.write("\n" + Convertor.convertHistory(getHistoryFromTaskManager()));
            } catch (IOException exception) {
                throw new ManagerSaveException("Ошибка сохранения");
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
    public Task createTask(Task task) {
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
        try {
            try (BufferedReader buffer = new BufferedReader(new FileReader(file,
                    Charset.defaultCharset()))) {
                String line;
                boolean isPreviosEmpty = false;
                while (buffer.ready()) {
                    line = buffer.readLine();
                    if (isPreviosEmpty) {
                        historyManager.add(Convertor.convertHistoryFromStringAndAdd(line));
                    } else if (line.isEmpty()) {
                        isPreviosEmpty = true;
                    }
                    if (Convertor.convertStringToTaskAndLoad(line).getType().equals(TaskType.SUBTASK)) {
                        Subtask subtask = (Subtask) Convertor.convertStringToTaskAndLoad(line);
                        addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
                        subtasks.put(subtask.getId(), subtask);
                    } else if (Convertor.convertStringToTaskAndLoad(line).getType().equals(TaskType.EPIC)) {
                        Epic epic = (Epic) Convertor.convertStringToTaskAndLoad(line);
                        epics.put(epic.getId(), epic);
                    } else if (Convertor.convertStringToTaskAndLoad(line).getType().equals(TaskType.EPIC)) {
                        Task task = Convertor.convertStringToTaskAndLoad(line);
                        tasks.put(task.getId(), task);
                    }

                }
            } catch (IOException e) {
                throw new  ManagerSaveException("Ошибка чтения");
            }
        } catch (ManagerSaveException managerSaveException) {
            managerSaveException.getMessage();
        }
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

    public String readFile() { //для тестов
        String fullLine = "";
        try (BufferedReader buffer = new BufferedReader(new FileReader(file,
                Charset.defaultCharset()))) {
            String line;
            while (buffer.ready()) {
                line = buffer.readLine();
                fullLine = String.join("\n", fullLine, line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения");
        }

        return fullLine.trim();
    }

    private ArrayList<Integer> getAllIds() {
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

}
