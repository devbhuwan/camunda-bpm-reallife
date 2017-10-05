package e2e.test;

import cucumber.api.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@Slf4j
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "classpath:features/order")
public class OrderPaymentScenario extends E2ETest {

}
