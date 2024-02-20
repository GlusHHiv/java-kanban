package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class EpicTest {
    private static final Epic epic = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);


    @Test
    public void addSubtask_DoesNotAddEpicIdInSubtasks() {
        epic.addSubtasks(0);
        Assertions.assertEquals(0, epic.getSubtasks().size(), "Эпик в числе своих сабтасков.");
    }

    @Test
    public void equals_compareSameEpics() {
        Epic epic1 = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);
        epic.addSubtasks(1);
        epic1.addSubtasks(1);
        Assertions.assertTrue(epic.equals(epic1), "Одинаковые Эпики не равны.");
    }

}