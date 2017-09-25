package e2e.test;


import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.joda.time.Duration;
import org.junit.ClassRule;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:Feature/createOrder.feature")
public class E2ETest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .saveLogsTo("target/dockerLogs/dockerComposeRuleTest")
            .waitingForService("rabbitmq", HealthChecks.toHaveAllPortsOpen())
            .waitingForService("order-service", E2ETest::serviceCheck, Duration.standardMinutes(1))
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

}
