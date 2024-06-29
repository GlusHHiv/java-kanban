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
import model.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private InMemoryTaskManager manager;

    public EpicHandler(TaskManager manager) {
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
                Epic task = manager.getEpicById(id);
                if (task == null) {
                    sendNotFound(exchange, "epic with id: " + id + " does not exist");
                    return;
                }
                sendText(exchange, task.toString(), 200);
                return;
            case GET_ALL:
                sendText(exchange, manager.getEpics().toString(), 200);
                return;
            case GET_EPIC_SUBTASKS:
                id = HandlerUtil.getId(path);
                if (manager.getEpicSubtasks(id).isEmpty()) {
                    sendNotFound(exchange, "Epic with id:" + id + " does not exist");
                    return;
                }
                sendText(exchange, manager.getEpicSubtasks(id).toString(), 200);
                return;
            case DELETE_TASK:
                id = HandlerUtil.getId(path);
                manager.deleteEpicById(id);
                sendText(exchange, "Epic with id: "
                        + id
                        + " was deleted"
                        + "\nCurrent size of Epics: "
                        + manager.getEpics().size(), 200);
                return;
            case DELETE_ALL:
                manager.deleteAllEpics();
                sendText(exchange, "All Epics were deleted :)", 200);
                return;
            case UPDATE:
                if (manager.updateEpic(postHandler(exchange)) != null) {
                    sendText(exchange, "Epic was updated :)", 201);
                    return;
                }
                sendHasInteractions(exchange);
                return;
            case CREATE:
                if (manager.createEpic(postHandler(exchange)) == null) {
                    sendHasInteractions(exchange);
                    return;
                }
                sendText(exchange, String.valueOf(manager.getEpics().size()), 201);
                return;
            case UNKNOWN:
                sendNotFound(exchange, "URI was not accurate");
        }
    }

    private Epic postHandler(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
        Epic epic = gson.fromJson(body, Epic.class);
        return epic;
    }
}
