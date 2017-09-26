package e2e.test.steps;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class TaskCompleteUtil {

    public static <T> JsonObject variablesJson(T data) {
        JsonElement jsonElement = new Gson().toJsonTree(data);
        JsonObject jsonObject = new JsonObject();
        JsonObject valueJsonObject = new JsonObject();
        jsonObject.add("variables", valueJsonObject);
        jsonElement.getAsJsonObject()
                .entrySet().forEach(e -> {
            JsonObject value = new JsonObject();
            value.add("value", e.getValue());
            valueJsonObject.add(e.getKey(), value);
        });
        return jsonObject;
    }
}
