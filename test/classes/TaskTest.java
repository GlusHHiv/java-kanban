package classes;

import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class TaskTest {
    private static final Managers managers = new Managers();

    @BeforeEach
    void beforeAll() {
        managers.getDefault().createTask(new Task("первая задача", "описание один", 0, Status.NEW));
        managers.getDefault().createEpic(new Epic("первый эпик", "описание одинепик", 0, Status.NEW));
    }

    @Test
    public void checkTaskId() {
        Assertions.assertEquals(managers.getDefault().getTaskById(0), managers.getDefault().getTaskById(0));
    }

    @Test
    public  void checkTasksHeritage() {
        Assertions.assertEquals(managers.getDefault().getEpicById(1), managers.getDefault().getEpicById(1));
    }


}