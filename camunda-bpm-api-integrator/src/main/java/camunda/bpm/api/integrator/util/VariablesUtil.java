package camunda.bpm.api.integrator.util;

import com.google.gson.Gson;

import java.util.Map;

public class VariablesUtil {

    private static final Gson gson = new Gson();

    public static <T> T toTypeObject(Object o, Class<T> clazz) {
        if (o == null)
            return null;
        else if (clazz.equals(o.getClass()))
            return (T) o;
        else if (o instanceof Map)
            return gson.fromJson(gson.toJson(o), clazz);
        throw new UnsupportedOperationException("");
    }
}
