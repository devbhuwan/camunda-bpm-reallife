package camunda.bpm.api.integrator;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEngineRestServiceImpl implements AbstractEngineRestService {

    @Autowired
    private TaskResource taskResource;

    public TaskResource getTaskResource() {
        return taskResource;
    }
}
