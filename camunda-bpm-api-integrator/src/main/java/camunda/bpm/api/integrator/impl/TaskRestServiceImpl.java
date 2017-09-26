package camunda.bpm.api.integrator.impl;

import camunda.bpm.api.integrator.TaskRestService;
import com.google.gson.JsonObject;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskRestServiceImpl implements TaskRestService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    @Override
    public JsonObject queryTasks() {
        return null;
    }

    @Override
    public JsonObject completeTask() {
        return null;
    }
}
