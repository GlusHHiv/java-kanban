package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    protected void sendText(HttpExchange h, String text, int rCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h, String erorText) throws IOException{
        byte[] resp = erorText.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendHasInteractions(HttpExchange h) throws IOException {
        h.sendResponseHeaders(406, 0);
    }

    protected String getEndpoint(String path, String method) {
        String[] points = path.split("/");
        switch (method) {
            case "GET":
                if (points.length == 3) {
                    return "GET_TASK";
                }
                if (points.length == 4) {
                    return "GET_EPIC_SUBTASKS";
                }
                return "GET_ALL";
            case "DELETE":
                if (points.length == 3) {
                    return "DELETE_TASK";
                }
                return "DELETE_ALL";
            case "POST":
                if (points.length == 3) {
                    return "UPDATE";
                }
                return "CREATE";
            default:
                return "UNKNOWN";
        }
    }
}
