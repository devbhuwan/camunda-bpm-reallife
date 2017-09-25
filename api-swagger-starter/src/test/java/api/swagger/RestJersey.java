package api.swagger;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("jersey")
public class RestJersey {

    @GET
    @Path("ok")
    public String ok() {
        return "ok";
    }
}
