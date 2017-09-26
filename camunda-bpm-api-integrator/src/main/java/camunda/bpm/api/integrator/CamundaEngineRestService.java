package camunda.bpm.api.integrator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CamundaEngineRestService.PATH)
public class CamundaEngineRestService extends AbstractEngineRestServiceImpl {

    public static final String PATH = "";

    @Override
    @RequestMapping
    public TaskResource taskResource() {
        return super.getTaskResource();
    }

}
