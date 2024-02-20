package model;

import managers.Managers;

public class Subtask extends Task {
    private final Integer epicId;
    public Subtask(String name, String description, Status status, Integer id, Integer epicId) {
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


    public Integer getEpicId() {
        return epicId;
    }


}
