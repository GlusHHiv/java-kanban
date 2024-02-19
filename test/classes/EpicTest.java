package classes;

import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EpicTest {
    private final Managers managers = new Managers();
    private final Epic epic = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);


    @Test
    public void addEpicToEpic() {
        epic.addSubtasks(0, epic);
        Assertions.assertFalse(epic.getSubtasks().contains(0));
    }
}