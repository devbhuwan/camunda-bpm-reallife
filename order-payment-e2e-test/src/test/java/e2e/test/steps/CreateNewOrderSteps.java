package e2e.test.steps;

import camunda.order.domain.Order;
import com.google.gson.JsonObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;

import java.util.List;

import static e2e.test.E2ETest.docker;

public class CreateNewOrderSteps {

    private static final String REST_CAMUNDA_ENGINE_ORDER_SERVICE = "/rest/engine/order-service";
    private final int externalPort = docker.containers().container("order-service").port(8080).getExternalPort();
    private ValidatableResponse createNewOrderProcessResponse;
    private ValidatableResponse submitDataEntryResponse;

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
        RestAssured.given().contentType(ContentType.JSON)
                .port(externalPort)
                .get("/rest/engine/").as(JsonObject.class);
    }

    @When("^Submit Data Entry Form$")
    public void submitDataEntryForm(List<Order> orders) {
        this.submitDataEntryResponse = RestAssured.
                given()
                .contentType(ContentType.JSON)
                .port(externalPort)
                .post(REST_CAMUNDA_ENGINE_ORDER_SERVICE + "/task/{id}/submit-form", TaskCompleteUtil.variablesJson(orders.get(0)))
                .then();
    }

    @Then("^Order is created and publish event for payment process$")
    public void orderIsCreatedAndPublishEventForPaymentProcess() throws Throwable {
        submitDataEntryResponse.assertThat().statusCode(200);
    }


}
