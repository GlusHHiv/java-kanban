import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String command = "0";
        while (!command.equals("9")){
            System.out.println("1 - вывести все звдвчи\n" +
                    "2 - удалить задачу\n" +
                    "3 - удалить подзадачу\n" +
                    "4 - удалить эпик\n" +
                    "5 - удалить все задачи(эпики и подзадачи в том числе)\n" +
                    "6 - создать задачу\n" +
                    "7 - вывести все подзадачи в эпике\n" +
                    "9 - выход");

            command = scanner.nextLine();
            switch (command) {
                case "1":
                    taskManager.printAllTasks();
                    break;
                case "2":
                    System.out.println("id Задачи:");
                    taskManager.deleteTaskById(scanner.nextInt());
                    break;
                case "3":
                    System.out.println("id Подзадачи:");
                    taskManager.deleteSubById(scanner.nextInt());
                    break;
                case "4":
                    System.out.println("id Эпика:");
                    taskManager.deleteEpicById(scanner.nextInt());
                    break;
                case "5":
                    taskManager.deleteAllTasks();
                    break;
                case "6":
                    taskManager.createTask();
                    break;
                case "7":
                    taskManager.printSubtasksInEpic();
                    break;
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }
}
