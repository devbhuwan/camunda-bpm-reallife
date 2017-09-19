package e2e.test.order.payment;


import io.restassured.RestAssured;
import org.junit.Test;

public class OrderPaymentE2ETest {

    @Test
    public void createOrderWithPayment() {
        RestAssured.
                given()
                .post("/order/create");
    }

}
