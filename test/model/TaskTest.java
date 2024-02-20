package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class TaskTest {
    private final Task task = new Task("первая задача", "описание один", 0, Status.NEW);


    @Test
    public void shouldReturnTheSameNameAsIntended() {
        Assertions.assertEquals("первая задача", task.getName());
    }

    @Test
    public void shouldReturnTheSameDescrtiptionAsIntended() {
        Assertions.assertEquals("описание один", task.getDescription());
    }

    @Test
    public void shouldReturnTheSameStatusAsIntended() {
        Assertions.assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    public void shouldReturnTheSameIdAsIntended() {
        Assertions.assertEquals(0, task.getId());
    }



}