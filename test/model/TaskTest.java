package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class TaskTest {
    private final Task task = new Task("первая задача", "описание один", 0, Status.NEW);

    @Test
    public void equals_compareSameTasks() {
        Assertions.assertTrue(task.equals(new Task("первая задача",
                "описание один",
                0,
                Status.NEW)), "Одинаковые таски не равны.");
    }
}