package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SubtaskTest {

    private Subtask subtask;

    @BeforeEach
    public void beforeEach() {
        subtask = new Subtask("первый саб_епик1", "описание одинсаб", Status.NEW, 1, 0);
    }
     @Test
     public void setEpicId_addSubtaskAsEpic() {
         subtask.setEpicId(1);
         Assertions.assertNotNull(subtask, "Сабтаск не создан");
         Assertions.assertNotEquals(1, subtask.getEpicId(), "Сабтаск добавил самого себя в эпик");
     }

     @Test
     public void equals_compareSameSubtasks() {
         Assertions.assertTrue(subtask.equals(new Subtask("первый саб_епик1",
                 "описание одинсаб",
                 Status.NEW,
                 1,
                 0)), "Одинаковые сабтаски не равны.");
     }
}