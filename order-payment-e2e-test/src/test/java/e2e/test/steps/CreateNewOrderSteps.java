package e2e.test.steps;

import camunda.bpm.api.integrator.TaskRestService;
import camunda.bpm.api.integrator.dto.TaskDto;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.hamcrest.CoreMatchers;

import static e2e.test.E2ETest.docker;

public class CreateNewOrderSteps {

    private final int externalPort = docker.containers().container("order-service").port(8080).getExternalPort();
    private ValidatableResponse createNewOrderProcessResponse;
    private ValidatableResponse submitDataEntryResponse;
    private TaskDto orderDataEntryTask;

    @When("^I create a new order$")
    public void iCreateANewOrder() {
        this.createNewOrderProcessResponse = RestAssured.
                given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .post("/order/create").then();
    }

    @Then("^the response is (.*)$")
    public void theResponseIsSuccessfullyCreatedYourAnOrder(String responseMessage) throws Throwable {
        this.createNewOrderProcessResponse.assertThat().statusCode(200)
                .content(CoreMatchers.equalTo(responseMessage));
    }

    @Given("^Order Data Entry Form$")
    public void orderDataEntryForm() {
        orderDataEntryTask = RestAssured.given().contentType(ContentType.JSON)
                .port(externalPort)
                .get(TaskRestService.GET_TASKS_BY_PROCESS_INSTANCE_ID, 5).as(TaskDto[].class)[0];
    }

    @When("^Submit Data Entry Form itemName is (.*) and quantity is (\\d+)$")
    public void submitDataEntryForm(String itemName, Integer quantity) {
        VariableMap variables = Variables.createVariables();
        variables.putValue("itemName", itemName);
        variables.putValue("quantity", quantity);
        this.submitDataEntryResponse = RestAssured.
                given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .body(variables)
                .post(TaskRestService.POST_COMPLETE_TASK_WITH_BODY, orderDataEntryTask.getId())
                .then();
    }

    @Then("^Order is created and publish event for payment process$")
    public void orderIsCreatedAndPublishEventForPaymentProcess() throws Throwable {
        submitDataEntryResponse.assertThat().statusCode(200);
    }


}
