package api.swagger;

import io.swagger.jaxrs.listing.ApiListingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.test.context.TestConfiguration;

import javax.ws.rs.ApplicationPath;

@TestConfiguration
@ApplicationPath("jersey")
public class App extends ResourceConfig {

    public App() {
        register(RestJersey.class);
        register(ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }

}
