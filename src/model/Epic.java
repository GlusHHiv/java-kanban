package model;

import managers.Managers;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description, Integer id, Status status) {
        super(name, description, id, status);

    }

    public void addSubtasks(int subId) {
        if (getId() != subId) {
            subtasks.add(subId);
        }
    }

    @Override
    public String toString() {
        return  "\nName: " + getName() +
                ", id: " + getId() +
                ", description: " + "'" + getDescription() +
                "', status: " + getStatus() +
                ", Subtasks: " + subtasks.toString();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    public void deleteSubTasks() {
        subtasks.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(getName(), otherEpic.getName()) &&
                Objects.equals(getDescription(), otherEpic.getDescription()) &&
                Objects.equals(getId(), otherEpic.getId()) &&
                Objects.equals(getStatus(), otherEpic.getStatus()) &&
                Objects.deepEquals(getSubtasks(), otherEpic.getSubtasks());
    }

}
