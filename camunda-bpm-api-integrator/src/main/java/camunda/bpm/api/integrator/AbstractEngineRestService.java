package camunda.bpm.api.integrator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AbstractEngineRestService.ROOT_PATH)
public interface AbstractEngineRestService {

    String ROOT_PATH = "/api/engine/{name}";
}
