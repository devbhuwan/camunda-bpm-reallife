package e2e.test.order.payment;


import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.joda.time.Duration;
import org.junit.ClassRule;
import org.junit.Test;

public class OrderPaymentE2ETest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .saveLogsTo("target/dockerLogs/dockerComposeRuleTest")
            .waitingForService("rabbitmq", HealthChecks.toHaveAllPortsOpen())
            .waitingForService("order-service", OrderPaymentE2ETest::serviceCheck, Duration.standardMinutes(1))
            .build();

    private static SuccessOrFailure serviceCheck(Container container) {
        try {
            Response response = RestAssured.given()
                    .port(container.port(8080).getExternalPort())
                    .get("/info").andReturn();
            return SuccessOrFailure.fromBoolean(
                    response.getStatusCode() == HttpStatus.SC_OK, "service health is not good.");
        } catch (Exception e) {
            return SuccessOrFailure.fromException(e);
        }
    }

    @Test
    public void createOrderWithPayment() {
        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .port(docker.containers().container("order-service").port(8080).getExternalPort())
                .post("/order/create").then()
                .assertThat().statusCode(200).content(CoreMatchers.equalTo("Successfully created your an order"));
    }

}
