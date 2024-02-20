package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SubtaskTest {
     private final Subtask subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 1, 1);

    @Test
    public void shouldReturnTheSameNameAsIntended() {
        Assertions.assertEquals("первый саб_епик1", subtask.getName());
    }

    @Test
    public void shouldReturnTheSameDescrtiptionAsIntended() {
        Assertions.assertEquals("описание одинсаб", subtask.getDescription());
    }

    @Test
    public void shouldReturnTheSameStatusAsIntended() {
        Assertions.assertEquals(Status.NEW, subtask.getStatus());
    }

    @Test
    public void shouldReturnTheSameIdAsIntended() {
        Assertions.assertEquals(1, subtask.getId());
    }

    @Test
    public void shouldReturnTheSameEpicIdAsIntended() {
        Assertions.assertEquals(1, subtask.getEpicId());
    }

}