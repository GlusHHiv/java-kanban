import classes.Status;
import classes.Subtask;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.createTask(new classes.Task("первая задача", "описание один", 0, Status.NEW));
        taskManager.createTask(new classes.Task("вторая задача", "описание два", 0, Status.DONE));
        taskManager.createEpic(new classes.Epic("первый эпик", "описание одинепик", 0, Status.NEW));
        taskManager.createSubTask(new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 0, 2));
        taskManager.createEpic(new classes.Epic("второй эпик", "описание одинепик", 0, Status.NEW));
        taskManager.createSubTask(new Subtask("первый саб_епик2", "описание одинсаб", Status.NEW, 0, 4));
        taskManager.createSubTask(new Subtask("второй саб_епик2", "описание двасаб", Status.IN_PROGRESS, 0, 4));
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
    }
}
