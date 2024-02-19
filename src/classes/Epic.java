package classes;

import managers.Managers;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<>();
    private Managers managers = new Managers();

    public Epic(String name, String description, Integer id, Status status) {
        super(name, description, id, status);

    }

    public void addSubtasks(int id, Task sub) {
         if(!(managers.getDefault().getSubtasks().contains(sub))){
            return;
        }
        subtasks.add(id);
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

}
