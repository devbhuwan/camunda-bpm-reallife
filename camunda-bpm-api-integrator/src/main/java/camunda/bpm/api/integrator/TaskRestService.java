package camunda.bpm.api.integrator;


import camunda.bpm.api.integrator.dto.TaskDto;
import camunda.bpm.api.integrator.dto.VariableValueDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

public interface TaskRestService extends AbstractEngineRestService {

    String TASK_REST_SERVICE_BASE_PATH = "/task";
    String GET_TASKS_BY_PROCESS_INSTANCE_ID = TASK_REST_SERVICE_BASE_PATH + "/{processInstanceId}";
    String GET_TASK_FORM_VARIABLES_BY_TASK_ID = TASK_REST_SERVICE_BASE_PATH + "/{taskId}/form-variables";
    String POST_COMPLETE_TASK_WITH_BODY = TASK_REST_SERVICE_BASE_PATH + "/{taskId}/complete";

    @GetMapping(GET_TASKS_BY_PROCESS_INSTANCE_ID)
    List<TaskDto> queryTasks(String processInstanceId);

    @GetMapping(GET_TASK_FORM_VARIABLES_BY_TASK_ID)
    Map<String, VariableValueDto> queryTaskFormVariables(String taskId);

    @PostMapping(POST_COMPLETE_TASK_WITH_BODY)
    void submitTaskForm(String taskId, Map<String, Object> variables);

}
