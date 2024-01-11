package classes;

import classes.Status;

public class Subtask extends Task {
    private final int epicId;
    public Subtask(String name, String description, Status status, Integer id, int epicId) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "\nName: " + getName() +
                ", id: " + getId() +
                ", description: " + "'" + getDescription() +
                "', status: " + getStatus();
    }


    public int getEpicId() {
        return epicId;
    }
}
