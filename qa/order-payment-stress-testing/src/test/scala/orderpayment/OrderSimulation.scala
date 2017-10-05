package orderpayment

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class OrderSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:18080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val orderFeeder = csv("order.csv").random

  val orderScenario = scenario("Create New Order")
    .feed(orderFeeder)
    .exec(http("POST order")
      .post("/order/create")
      .body(StringBody(
        """{
          |"itemName":"${itemName}",
          |"quantity":"${quantity}",
          |}""".stripMargin)).asJSON
      .check(jsonPath("$").saveAs("response")))
    .exec(session => {
      println(session.get("response").asOption[String])
      session
    })

  before({
    TestStarter.start()
  })

  setUp(orderScenario.inject(atOnceUsers(1)).protocols(httpConf))

  after({
    TestStarter.shutdown()
  })

}
