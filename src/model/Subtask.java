package model;

import java.util.Objects;

public class Subtask extends Task {


    public Subtask(String name, String description, Status status, Integer id, Integer epicId) {
        super(name, description, id, status, TaskType.SUBTASK);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "\nName: " + getName() +
                ", id: " + getId() +
                ", description: " + "'" + getDescription() +
                "', status: " + getStatus() +
                ", epicId: " + epicId +
                ", type: " + getType();
    }


    public Integer getEpicId() {
        return epicId;
    }

    public boolean setEpicId(int epicId) {
        if (epicId == getId()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Subtask otherSubtask = (Subtask) obj;
        return Objects.equals(getName(), otherSubtask.getName()) &&
                Objects.equals(getDescription(), otherSubtask.getDescription()) &&
                Objects.equals(getId(), otherSubtask.getId()) &&
                Objects.equals(getStatus(), otherSubtask.getStatus()) &&
                Objects.deepEquals(getEpicId(), otherSubtask.getEpicId());
    }
}
