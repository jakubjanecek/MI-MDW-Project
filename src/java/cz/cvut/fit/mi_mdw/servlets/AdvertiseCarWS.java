package cz.cvut.fit.mi_mdw.servlets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface AdvertiseCarWS {

    @GET
    @Path("/")
    @Produces("text/plain")
    String advertise();
}
