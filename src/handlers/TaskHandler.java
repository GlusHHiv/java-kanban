package handlers;

import Util.HandlerUtil;
import adapters.DurationTypeAdapter;
import adapters.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;
import model.EndPoint;
import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private InMemoryTaskManager manager;

    public TaskHandler(TaskManager manager) {
        this.manager = (InMemoryTaskManager) manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        EndPoint endpoint = HandlerUtil.getEndpoint(path, method);
        Integer id;
        switch (endpoint) {
            case GET_TASK:
                id = HandlerUtil.getId(path);
                Task task = manager.getTaskById(id);
                if (task == null) {
                    sendHasInteractions(exchange);
                    return;
                }
                sendText(exchange, task.toString(), 200);
                return;
            case GET_ALL:
                sendText(exchange, manager.getTasks().toString(), 200);
                return;
            case DELETE_TASK:
                id = HandlerUtil.getId(path);
                manager.deleteTaskById(id);
                sendText(exchange, "Task with id: "
                        + id
                        + " was deleted"
                        + "\nCurrent size of Tasks: "
                        + manager.getTasks().size(), 200);
                return;
            case DELETE_ALL:
                manager.deleteAllTasks();
                sendText(exchange, "All tasks were deleted :)", 200);
                return;
            case UPDATE:
                if (manager.updateTask(postHandler(exchange)) != null) {
                    sendText(exchange, "Task was updated :)", 201);
                    return;
                }
                sendHasInteractions(exchange);
            case CREATE:
                if (manager.createTask(postHandler(exchange)) == null) {
                    sendHasInteractions(exchange);
                }
                sendText(exchange, String.valueOf(manager.getTasks().size()), 201);
                return;
            case UNKNOWN:
                sendNotFound(exchange, "URI was not accurate");
        }
    }


    private Task postHandler(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new  GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
        Task task = gson.fromJson(body, Task.class);
        task.calculateEndTime();
        return task;
    }
}
