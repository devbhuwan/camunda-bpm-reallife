package e2e.test.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;

import static e2e.test.E2ETest.docker;

public class CreateNewOrderSteps {

    private ValidatableResponse createNewOrderResponse;

    @When("^I create a new order$")
    public void iCreateANewOrder() {
        this.createNewOrderResponse = RestAssured.
                given()
                .contentType(ContentType.JSON)
                .port(docker.containers().container("order-service").port(8080).getExternalPort())
                .post("/order/create").then();
    }

    @Then("^the response is (.*)$")
    public void theResponseIsSuccessfullyCreatedYourAnOrder(String responseMessage) throws Throwable {
        this.createNewOrderResponse.assertThat().statusCode(200)
                .content(CoreMatchers.equalTo(responseMessage));
    }
}
