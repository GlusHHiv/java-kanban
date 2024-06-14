package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EpicTest {
    private Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);
        epic.addSubtasks(1);
    }

    @Test
    public void addSubtask_DoesNotAddEpicIdInSubtasks() {
        int initialEpicSize = epic.getSubtasks().size();
        epic.addSubtasks(0);
        Assertions.assertEquals(initialEpicSize, epic.getSubtasks().size(), "Эпик в числе своих сабтасков.");
    }

    @Test
    public void equals_compareSameEpics() {
        Epic epic1 = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);
        epic1.addSubtasks(1);
        Assertions.assertTrue(epic.equals(epic1), "Одинаковые Эпики не равны.");
    }

}