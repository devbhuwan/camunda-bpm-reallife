package orderpayment

import com.palantir.docker.compose.DockerComposeRule
import com.palantir.docker.compose.connection.Container
import com.palantir.docker.compose.connection.waiting.{HealthChecks, SuccessOrFailure}
import io.restassured.RestAssured
import org.apache.http.HttpStatus
import org.joda.time.Duration

object TestStarter {

  val docker: DockerComposeRule = DockerComposeRule.builder
    .file("src/test/resources/docker-compose.yml")
    .saveLogsTo("target/dockerLogs/dockerComposeRuleTest")
    .waitingForService("rabbitmq", HealthChecks.toHaveAllPortsOpen)
    .waitingForService("order-service", serviceCheck, Duration.standardMinutes(5))
    .build

  def serviceCheck(container: Container): SuccessOrFailure = try {
    val response = RestAssured.given.port(container.port(8080).getExternalPort).get("/info").andReturn
    SuccessOrFailure.fromBoolean(response.getStatusCode == HttpStatus.SC_OK, "service health is not good.")
  } catch {
    case e: Exception =>
      SuccessOrFailure.fromException(e)
  }

  def start(): Unit = docker.before()

  def shutdown(): Unit = docker.after()

  def orderService: Container = docker.containers().container("order-service")

  def paymentService: Container = docker.containers().container("payment-service")

}
