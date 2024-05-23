package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    private final static DateTimeFormatter NUMBER_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    protected final static DateTimeFormatter TO_STRING_FORMAT = DateTimeFormatter.ofPattern("yy:MM:dd HH:mm");

    public Task() {
        name = "null Task";
    }

    public Task(String name, String description, Integer id, Status status, Integer duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = TaskType.TASK;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
        calculateEndTime();
    }

    public Task(String name, String description, Integer id, Status status, TaskType type, Integer duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
        calculateEndTime();
    }


    @Override
    public String toString() {
        return "\nName='" + name +
                ", description='" + description +
                ", id: " + getId() +
                ", status='" + status +
                "startTime" + startTime.format(TO_STRING_FORMAT);
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
                Objects.equals(status, otherTask.status);
    }

    public boolean isNullTask() {
        return name.equals("null Task");
    }

    public TaskType getType() {
        return type;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void addMinutes(Long minutes) {
        duration = duration.plusMinutes(minutes);
    }

    public void calculateEndTime() {
        endTime = startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getStartTimeInNumber() {
        return Long.parseLong(startTime.format(NUMBER_FORMAT));
    }

    public Long getEndTimeInNumber() {
        return Long.parseLong(endTime.format(NUMBER_FORMAT));
    }
}

