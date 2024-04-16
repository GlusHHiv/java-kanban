package model;

import java.util.ArrayList;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Integer id;
    private Status status;

    protected Integer epicId;
    private  TaskType type;
    protected ArrayList<Integer> subtasks = new ArrayList<>();

    public Task() {
        name = "null Task";
    }

    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = TaskType.TASK;
    }

    public Task(String name, String description, Integer id, Status status, TaskType type) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    @Override
    public String toString() {
        return "\nName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id: " + getId() +
                ", status='" + status + '\'';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(name, otherTask.name) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(id, otherTask.id) &&
                Objects.equals(status, otherTask.status);
    }

    public boolean isNullTask() {
        return name.equals("null Task");
    }

    public TaskType getType() {
        return type;
    }
}

