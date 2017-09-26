package camunda.bpm.api.integrator.impl;

import camunda.bpm.api.integrator.TaskRestService;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class TaskRestServiceImpl implements TaskRestService {

    @Override
    public JsonObject queryTasks() {
        return null;
    }

    @Override
    public JsonObject completeTask() {
        return null;
    }
}
