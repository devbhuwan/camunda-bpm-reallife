package e2e.test;

import camunda.order.domain.ImmutableOrder;
import camunda.order.domain.Order;
import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;

import static e2e.test.E2ETest.docker;


@Slf4j
public class CreateOrderStepDefinitions implements En {

    private Order order;

    public CreateOrderStepDefinitions() {
        Given("^order hello$", () -> {
            order = ImmutableOrder.builder().build();
        });
        When("^users create an new order$", () -> {
            RestAssured.
                    given()
                    .contentType(ContentType.JSON)
                    .port(docker.containers().container("order-service").port(8080).getExternalPort())
                    .post("/order/create").then()
                    .assertThat().statusCode(200).content(CoreMatchers.equalTo("Successfully created your an order"));

        });
        Then("^the server should create an order and return success$", () -> {
        });

    }
}
