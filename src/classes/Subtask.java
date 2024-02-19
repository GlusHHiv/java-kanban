package classes;

import classes.Status;
import managers.Managers;

public class Subtask extends Task {
    private final Integer epicId;
    private final Managers managers = new Managers();
    public Subtask(String name, String description, Status status, Integer id, Integer epicId) {
        super(name, description, id, status);
        this.epicId = setEpicId(epicId);
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

    public Integer setEpicId(int id) {
        if(!managers.getDefault().getEpics().contains(managers.getDefault().getEpicById(id))) {
            return null;
        }
        return id;
    }

}
