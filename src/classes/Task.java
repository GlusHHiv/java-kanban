package classes;

import classes.Status;

public class Task {
    private String name;
    private String description;
    private  Integer id;
    protected Status status;

    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        if(id == null) {
            id = 0;
        }
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
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
}

