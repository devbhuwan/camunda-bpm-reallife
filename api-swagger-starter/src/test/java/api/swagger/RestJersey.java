package api.swagger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("jersey")
public class RestJersey {

    @GET
    @Path("ok")
    public String ok() {
        return "ok";
    }
}
