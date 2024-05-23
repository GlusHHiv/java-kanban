package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class LinkedMapListTest {
    private LinkedMapList<Task> linkedMapList;

    @BeforeEach
    public void beforeEach() {
        linkedMapList = new LinkedMapList<>();
        linkedMapList.linkLast(new Task("Test task",
                "test description",
                0,
                Status.NEW,
                40,
                LocalDateTime.now()), 0);
    }

    @Test
    public void linkLast() {
        Assertions.assertEquals(new Task("Test task",
                "test description",
                0,
                Status.NEW,
                40,
                LocalDateTime.now()),
                linkedMapList.get(0),
                "Задачи не равны.");
        Assertions.assertEquals(1, linkedMapList.size(), "Размер не увеличился.");
    }

    @Test
    public void removeElement_removeNullElement() {
        Assertions.assertFalse(linkedMapList.removeElement(3), "Удален несущетвующий элемент.");
    }

    @Test
    public void removeElement() {
        Assertions.assertTrue(linkedMapList.removeElement(0), "Удаления не произошло.");
    }
}

