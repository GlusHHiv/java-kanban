package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class TaskTest {
    private final Task task = new Task("Test task",
            "test description",
            0,
            Status.NEW,
            40,
            LocalDateTime.now().plusHours(1));

    @Test
    public void equals_compareSameTasks() {
        Assertions.assertTrue(task.equals(new Task("Test task",
                "test description",
                0,
                Status.NEW,
                40,
                LocalDateTime.now().plusHours(1))), "Одинаковые таски не равны.");
    }
}
