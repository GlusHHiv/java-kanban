public class Subtask extends Task {

    Epic epic;
    public Subtask(Task task, Epic epic) {
        super(task.name, task.description, task.id, task.status);
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nid: " + id +
                "\ndescription: " + "'" + description +
                "'\nstatus: " + status;
    }
}
