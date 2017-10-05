package e2e.test;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        format = {"pretty"},
        features = {"classpath:features/order/"},
        glue = "lv.ctco.cukes",
        strict = true
)
public class OrderE2ETest extends E2ETest {

}
