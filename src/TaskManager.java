import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    int id = 0;

    public void createSubTask(){
        int epicId;

        System.out.println("id эпика для этой подзадачи:");
        epicId = scanner.nextInt();
        
        Subtask subtask = new Subtask(tasks.get(id),  epics.get(epicId));
        epics.get(epicId).addSubtasks(subtask);

        subtasks.put(id, subtask);
    }

    public void createTask(){
        String  command;
        System.out.println("Имя:");
        String name = scanner.nextLine();
        System.out.println("Описание:");
        String description = scanner.nextLine();


        Task task = new Task(name, description, id, "NEW");

        tasks.put(id, task);

        System.out.println("эпик - 1 \nсаб - 2\nни то ни то - 3");
        command = scanner.nextLine();
        switch (command){
            case "1":
                createEpic();
                break;
            case "2":
                printEpics();
                createSubTask();
                break;
        }
        id++;

    }
    public void createEpic(){
        Epic epic = new Epic(tasks.get(id));
        epics.put(id, epic);

    }

    public void printEpics(){
        if(epics.isEmpty()) {
            System.out.println("Список Задач пуст");
        }else {
            for (Epic epic: epics.values()) {
                System.out.println(epic.toString() +"\n");
            }
        }
    }

    public void deleteAllTasks(){
        subtasks.clear();
        epics.clear();
        tasks.clear();
    }

    public void printSubtasks() {
        for (Subtask sup : subtasks.values()) {
            System.out.println(sup.toString());
        }
    }

    public void printAllTasks(){
        System.out.println("ЗАДАЧИ:");
        for(Task task: tasks.values()){
            System.out.println(task.name + " id - " + task.id);
        }

        System.out.println("\nЭПИКИ:");
        printEpics();

        System.out.println("\nПОДЗАДАЧИ:");
        printSubtasks();
    }

   public void updateTask(Task task){
       tasks.put(task.id, task);
   }

   public void updateEpic(Epic epic){
        epic.defineStatus();
        epics.put(epic.id, epic);
        updateTask(epic);
   }

   public void updateSubtasks(Subtask subtask) {
        subtasks.get(subtask.id).epic.subtasks.put(subtask.id, subtask);
        updateEpic(subtasks.get(subtask.id).epic);
        subtasks.put(subtask.id, subtask);
        updateTask(subtask);
   }

   public void printSubtasksInEpic() {
       System.out.println("id эпика для вывода его подзадач:");
       int epicId = scanner.nextInt();
       System.out.println(epics.get(epicId).name);
       for (Subtask sub : epics.get(epicId).subtasks.values()) {
           System.out.println(sub.toString());
       }
   }

   public void deleteEpicById(int epicId) {
       for (Subtask sub: epics.get(epicId).subtasks.values()) {
           tasks.remove(sub.id);
           subtasks.remove(sub.id);
       }
        epics.remove(epicId);
   }

   public void deleteTaskById(int taskId) {
        if(!tasks.containsKey(taskId)) {
            System.out.println("Задачи с таким Id нет");
            return;
        }

        if(epics.containsKey(taskId)) {
            deleteEpicById(taskId);
        } else if(subtasks.containsKey(taskId)) {
            deleteSubById(taskId);
        }else {
            tasks.remove(taskId);
            subtasks.remove(taskId);
            epics.remove(taskId);
        }
   }

   public void deleteSubById(int subID){
        subtasks.get(subID).epic.subtasks.remove(subID);
        updateEpic(subtasks.get(subID).epic);
        subtasks.remove(subID);
        tasks.remove(subID);
   }

}
