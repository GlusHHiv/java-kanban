import java.util.HashMap;

public class Epic extends Task {

    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(Task task) {
        super(task.name, task.description, task.id, task.status);
    }

    public void addSubtasks(Subtask subtask){
        subtasks.put(subtask.id, subtask);
        defineStatus();
    }
    public void defineStatus(){
        int doneCount = 0;
        int inProgressCount = 0;

        for (Subtask sub : subtasks.values()) {
            switch ((sub.status)) {
                case "NEW":
                    break;
                case "IN_PROGRESS":
                    inProgressCount++;
                    break;
                case "DONE":
                    doneCount++;
                    break;
            }
        }

        if ((doneCount == subtasks.size()) && (!subtasks.isEmpty())) {
            status = "DONE";
        } else if(inProgressCount > 0 || doneCount > 0) {
            status = "IN_PROGRESS";
        }else {
            status = "NEW";
        }
    }

    @Override
    public String toString() {
        return "ЭПИК" +
                "\nName: " + name +
                "\nid: " + id +
                "\ndescription: " + "'" + description +
                "'\nstatus: " + status +
                "\n  Subtasks: \n" + subtasks.toString();
    }

}
