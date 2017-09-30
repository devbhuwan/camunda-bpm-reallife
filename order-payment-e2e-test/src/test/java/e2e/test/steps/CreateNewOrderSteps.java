package e2e.test.steps;

import camunda.bpm.api.integrator.AbstractEngineRestService;
import camunda.bpm.api.integrator.dto.TaskDto;
import camunda.order.domain.Order;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import static camunda.bpm.api.integrator.TaskRestService.GET_TASKS_BY_PROCESS_INSTANCE_ID;
import static camunda.bpm.api.integrator.TaskRestService.POST_COMPLETE_TASK_WITH_BODY;
import static e2e.test.E2ETest.docker;

public class CreateNewOrderSteps {

    private static Response submitDataEntryResponse;
    private static Response createOrderResponse;
    private static Order order;
    private static Response retrievePaymentResponse;
    private final int externalPort = docker.containers().container("order-service").port(8080).getExternalPort();
    private TaskDto orderDataEntryTask;

    @When("^I create a new order$")
    public void iCreateANewOrder() {
        createOrderResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .post("/order/create");
    }

    @Then("^the response is (.*)$")
    public void theResponseIsSuccessfullyCreatedYourAnOrder(String responseMessage) throws Throwable {
        createOrderResponse.then().assertThat().statusCode(200);
    }

    @Given("^Order Data Entry Form$")
    public void orderDataEntryForm() {
        orderDataEntryTask = getCurrentTask();
    }


    @When("^Submit Data Entry Form itemName is (.*) and quantity is (\\d+)$")
    public void submitDataEntryForm(String itemName, Integer quantity) {
        VariableMap variables = Variables.createVariables();
        order = new Order(itemName, quantity);
        variables.putValue("order", order);
        submitDataEntryResponse = completeTask(orderDataEntryTask.getId(), variables);
    }


    @Then("^Order is created as a draft$")
    public void orderIsCreatedAsADraft() {
        submitDataEntryResponse.then().assertThat().statusCode(200);
    }

    @When("^Retrieve Order Payment$")
    public void retrieveOrderPayment() {
        Order order = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .get("/order/all").as(Order[].class)[0];
        retrievePaymentResponse = completeTask(getCurrentTask().getId(), Variables.putValue("order", order));
    }

    private TaskDto getCurrentTask() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .get(engineURI(GET_TASKS_BY_PROCESS_INSTANCE_ID), createOrderResponse.asString())
                .as(TaskDto[].class)[0];
    }

    private String engineURI(String path) {
        return AbstractEngineRestService.ROOT_PATH + path;
    }

    private Response completeTask(String taskId, VariableMap variables) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .body(variables)
                .post(engineURI(POST_COMPLETE_TASK_WITH_BODY), taskId);
    }
}
