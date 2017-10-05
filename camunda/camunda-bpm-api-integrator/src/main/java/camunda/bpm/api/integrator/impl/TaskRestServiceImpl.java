package camunda.bpm.api.integrator.impl;

import camunda.CurrentUserIdentityProvider;
import camunda.bpm.api.integrator.TaskRestService;
import camunda.bpm.api.integrator.dto.TaskDto;
import camunda.bpm.api.integrator.dto.VariableValueDto;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class TaskRestServiceImpl implements TaskRestService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private CurrentUserIdentityProvider currentUserIdentityProvider;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @Override
    public List<TaskDto> queryTasks(@NotEmpty @PathVariable("processInstanceId") String processInstanceId) {
        log.info("Query tasks for [processInstanceId={}]", processInstanceId);
        return securedTaskQuery(taskService.createTaskQuery(), currentUserIdentityProvider)
                .processInstanceId(processInstanceId)
                .list().stream().map(task -> {
                    TaskDto dto = new TaskDto();
                    dto.setId(task.getId());
                    dto.setName(task.getName());
                    dto.setTenantId(task.getTenantId());
                    dto.setTaskDefinitionKey(task.getTaskDefinitionKey());
                    return dto;
                }).collect(Collectors.toList());
    }

    private TaskQuery securedTaskQuery(TaskQuery taskQuery, CurrentUserIdentityProvider currentUserIdentityProvider) {
        return taskQuery.active();
    }

    @Override
    public Map<String, VariableValueDto> queryTaskFormVariables(@NotEmpty @PathVariable("taskId") String taskId) {
        log.info("Query task form variables for [taskId={}]", taskId);
        return formService.getTaskFormData(taskId).getFormFields().stream()
                .collect(Collectors.toMap(FormField::getId, formField -> {
                    VariableValueDto valueDto = new VariableValueDto();
                    valueDto.setType(formField.getTypeName());
                    valueDto.setValue(formField.getValue().getValue());
                    return valueDto;
                }));
    }

    @Override
    public void submitTaskForm(@NotEmpty @PathVariable("taskId") String taskId, @RequestBody Map<String, Object> variables) {
        log.info("Submit Task Form [taskId={}, variables={}]", taskId, variables + "");
        Task task = securedTaskQuery(taskService.createTaskQuery(), currentUserIdentityProvider).taskId(taskId).singleResult();
        if (task != null)
            formService.submitTaskForm(taskId, variables);
    }
}
