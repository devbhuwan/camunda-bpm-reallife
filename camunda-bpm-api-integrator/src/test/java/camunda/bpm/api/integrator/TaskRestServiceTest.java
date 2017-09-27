package camunda.bpm.api.integrator;

import camunda.bpm.api.integrator.dto.TaskDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;

public class TaskRestServiceTest extends CamundaBpmApiIntegratorTest {

    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void GET_TASKS_BY_PROCESS_INSTANCE_ID_andThen_GET_TASK_FORM_VARIABLES_BY_TASK_ID_andThen_POST_COMPLETE_TASK_WITH_BODY() {
        ProcessInstance orderProcess = runtimeService.startProcessInstanceByKey("ORDER_PROCESS");
        TaskDto[] tasks = RestAssured.given().port(serverPort)
                .get(engineUrl(TaskRestService.GET_TASKS_BY_PROCESS_INSTANCE_ID), orderProcess.getId())
                .as(TaskDto[].class);

        Assertions.assertThat(tasks).hasSize(1);
        assertThat(orderProcess).isWaitingAt("ORDER_ENTRY");
        Map variables = RestAssured.given().port(serverPort)
                .get(engineUrl(TaskRestService.GET_TASK_FORM_VARIABLES_BY_TASK_ID), tasks[0].getId())
                .as(HashMap.class);

        Assertions.assertThat(variables)
                .containsOnlyKeys("itemName", "quantity");

        VariableMap formVariables = Variables.createVariables();
        formVariables.putValue("itemName", "Hello");
        formVariables.putValue("quantity", 10);
        RestAssured.given().port(serverPort)
                .contentType(ContentType.JSON)
                .body(formVariables)
                .post(engineUrl(TaskRestService.POST_COMPLETE_TASK_WITH_BODY), tasks[0].getId())
                .then().assertThat().statusCode(200);

        assertThat(orderProcess).isEnded();
    }

}