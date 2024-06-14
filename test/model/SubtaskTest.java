package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class SubtaskTest {

    private Subtask subtask;

    @BeforeEach
    public void beforeEach() {
        subtask = new Subtask("Test Sub",
                "Test",
                Status.NEW,
                0,
                1,
                20,
                LocalDateTime.now());
    }
     @Test
     public void setEpicId_addSubtaskAsEpic() {
         Assertions.assertNotNull(subtask, "Сабтаск не создан");
         Assertions.assertFalse(subtask.setEpicId(0), "Сабтаск добавил самого себя в эпик");
     }

     @Test
     public void equals_compareSameSubtasks() {
         Assertions.assertTrue(subtask.equals(new Subtask("Test Sub",
                 "Test",
                 Status.NEW,
                 0,
                 1,
                 20,
                 LocalDateTime.now())), "Одинаковые сабтаски не равны.");
     }
}
