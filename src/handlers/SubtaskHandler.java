package handlers;

import adapters.DurationTypeAdapter;
import adapters.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;
import model.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private InMemoryTaskManager manager;

    public SubtaskHandler(TaskManager manager) {
        this.manager = (InMemoryTaskManager) manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String endpoint = getEndpoint(path, method);
        int id;
        switch (endpoint) {
            case "GET_TASK":
                id = Integer.parseInt(path.split("/")[2]);
                Subtask subtask = manager.getSubtaskById(id);
                if (subtask == null) {
                    sendHasInteractions(exchange);
                }
                sendText(exchange, subtask.toString(), 200);
                return;
            case "GET_ALL":
                sendText(exchange, manager.getSubtasks().toString(), 200);
                return;
            case "DELETE_TASK":
                id = Integer.parseInt(path.split("/")[2]);
                manager.deleteSubtaskById(id);
                sendText(exchange, "Subtask with id: "
                        + id
                        + " was deleted"
                        + "\nCurrent size of Subtasks: "
                        + manager.getSubtasks().size(), 200);
                return;
            case "DELETE_ALL":
                manager.deleteAllSubtasks();
                sendText(exchange, "All Subtasks were deleted :)", 200);
                return;
            case "UPDATE":
                if (manager.updateSubtasks(postHandler(exchange)) != null) {
                    sendText(exchange, "Task was updated :)", 201);
                    return;
                }
                sendHasInteractions(exchange);
                sendNotFound(exchange, "Updated Subtask had a error");
            case "CREATE":
                if (manager.createSubTask(postHandler(exchange)) == null) {
                    sendHasInteractions(exchange);
                }
                sendText(exchange, String.valueOf(manager.getSubtasks().size()), 201);
                return;
            case "UKNOWN":
                sendNotFound(exchange, "URI was not accurate");
        }
    }

    private Subtask postHandler(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
        Subtask subtask = gson.fromJson(body, Subtask.class);
        subtask.calculateEndTime();
        return subtask;
    }
}
