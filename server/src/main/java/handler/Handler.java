package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Handler {
    protected <T> String toJson(T obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }

    protected <T> T fromJson(String json, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }
}
