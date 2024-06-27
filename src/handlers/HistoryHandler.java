package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private InMemoryTaskManager manager;

    public HistoryHandler(TaskManager manager) {
        this.manager = (InMemoryTaskManager) manager;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        sendText(exchange, manager.getHistoryFromTaskManager().toString(), 200);
    }
}
