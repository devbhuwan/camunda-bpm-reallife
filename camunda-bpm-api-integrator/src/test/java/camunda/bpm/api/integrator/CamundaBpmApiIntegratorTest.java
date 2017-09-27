package camunda.bpm.api.integrator;

import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamundaBpmApiIntegratorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class CamundaBpmApiIntegratorTest {

    @LocalServerPort
    protected int serverPort;

    protected String engineUrl(String url) {
        return TaskRestService.ROOT_PATH + url;
    }

}
