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

    public FileBackedTaskManager(File file, HistoryManager historyManager) {
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
                        writer.write(i + "," + Convertor.convertSubtaskToString(subtasks.get(i)));
                    } else if (epics.containsKey(i)) {
                        writer.write(i + "," + Convertor.convertEpicToString(epics.get(i)));
                    } else if (tasks.containsKey(i)) {
                        writer.write(i + "," + Convertor.convertTaskToString(tasks.get(i)));
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

    public  FileBackedTaskManager loadFromFile(File file1)  {
        try (BufferedReader buffer = new BufferedReader(new FileReader(file,
                Charset.defaultCharset()))) {
            String line;
            boolean isPreviosEmpty = false;
            while (buffer.ready()) {
                line = buffer.readLine();
                if (isPreviosEmpty) {
                    convertHistoryFromStringAndAdd(line);
                } else if (line.isEmpty()) {
                    isPreviosEmpty = true;
                }
                convertStringToTaskAndLoad(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения");
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

    private Task convertStringToTaskAndLoad(String line) {
        if(line.equals("id,type,name,status,description,epic")) {
            return null;
        } else if (line.isEmpty() || line.isBlank()) {
            return null;
        }
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2], str[4], taskId, Status.valueOf(str[3]));
            tasks.put(taskId, task);
            return task;
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            epics.put(taskId, epic);
            return epic;
        } else {
            Integer epicId = Integer.parseInt(str[5]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId);
            addSubtaskToEpic(epics.get(subtask.getEpicId()).getId(), subtask.getId());
            updateEpicStatus(epics.get(subtask.getEpicId()));
            subtasks.put(taskId, subtask);
            return subtask;
        }

    }

    private void convertHistoryFromStringAndAdd(String line) {
        String[] str = line.split(",");
        Integer taskId = Integer.parseInt(str[0]);
        if (str[1].equals("TASK")) {
            Task task = new Task(str[2], str[4], taskId, Status.valueOf(str[3]));
            historyManager.add(task);
        } else if (str[1].equals("EPIC")) {
            Epic epic = new Epic(str[2], str[4], taskId, Status.valueOf(str[3]));
            historyManager.add(epic);
        } else {
            Integer epicId = Integer.parseInt(str[5]);
            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), taskId, epicId);
            historyManager.add(subtask);
        }

    }
}