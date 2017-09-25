package camunda.bpm.api.integrator;

import camunda.ApplicationRoot;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = ApplicationRoot.class)
public class CamundaBpmApiIntegratorApplication {

    public static void main(String[] args) {
        CamundaRestResources.getResourceClasses().add(ApiListingResource.class);
        CamundaRestResources.getResourceClasses().add(SwaggerSerializers.class);
        SpringApplication.run(CamundaBpmApiIntegratorApplication.class, args);
    }

}
