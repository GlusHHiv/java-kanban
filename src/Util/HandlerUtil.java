package Util;

import model.EndPoint;

public  class HandlerUtil {
    public static EndPoint getEndpoint(String path, String method) {
        String[] points = path.split("/");
        switch (method) {
            case "GET":
                if (points.length == 3) {
                    return EndPoint.GET_TASK;
                }
                if (points.length == 4) {
                    return EndPoint.GET_EPIC_SUBTASKS;
                }
                return EndPoint.GET_ALL;
            case "DELETE":
                if (points.length == 3) {
                    return EndPoint.DELETE_TASK;
                }
                return EndPoint.DELETE_ALL;
            case "POST":
                if (points.length == 3) {
                    return EndPoint.UPDATE;
                }
                return EndPoint.CREATE;
            default:
                return EndPoint.UNKNOWN;
        }
    }

    public static Integer getId(String line) {
        if (line.length() < 3) {
            return null;
        }
        return Integer.parseInt(line.split("/")[2]);
    }
}
