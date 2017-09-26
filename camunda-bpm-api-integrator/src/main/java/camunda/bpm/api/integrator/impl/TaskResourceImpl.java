package camunda.bpm.api.integrator.impl;

import camunda.bpm.api.integrator.TaskResource;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class TaskResourceImpl implements TaskResource {

    @Override
    public JsonObject queryTasks() {
        return null;
    }

    @Override
    public JsonObject completeTask() {
        return null;
    }
}
