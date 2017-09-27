package camunda.bpm.api.integrator;

import camunda.ApplicationRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = ApplicationRoot.class)
public class CamundaBpmApiIntegratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaBpmApiIntegratorApplication.class, args);
    }

}
