package classes;

import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SubtaskTest {

    public final static Managers managers = new Managers();
    Subtask subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 1, 1);


    @BeforeAll
    public static void beforeAll() {
        managers.getDefault().createEpic(new Epic("первый эпик", "описание одинепик", 0, Status.NEW));
    }

    @Test
    public void addSubtaskAsEpic() {
        Assertions.assertNotEquals(1, subtask.getEpicId());
    }
}