package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class EpicTest {
    private static final Epic epic = new Epic("первый эпик", "описание одинепик", 0, Status.NEW);
    private static final ArrayList<Integer> arrayList = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        epic.addSubtasks(1);
        arrayList.add(1);
    }

    @Test
    public void shouldReturnTheSameNameAsIntended() {
        Assertions.assertEquals("первый эпик", epic.getName());
    }

    @Test
    public void shouldReturnTheSameDescrtiptionAsIntended() {
        Assertions.assertEquals("описание одинепик", epic.getDescription());
    }

    @Test
    public void shouldReturnTheSameStatusAsIntended() {
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldReturnTheSameIdAsIntended() {
        Assertions.assertEquals(0, epic.getId());
    }

    @Test
    public void shouldReturnTheSameSubtasksAsIntended() {
        Assertions.assertEquals(arrayList, epic.getSubtasks());
    }
}