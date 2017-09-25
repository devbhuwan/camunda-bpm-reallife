package camunda.bpm.api.integrator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamundaBpmApi {

    @GetMapping("rest")
    public String ok() {
        return "ok";
    }

    @GetMapping("rest2")
    public String ok2() {
        return "ok";
    }

    @GetMapping("api")
    public String api() {
        return "ok";
    }
}
