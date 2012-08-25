package cz.cvut.fit.mi_mdw.ws;

import cz.cvut.fit.mi_mdw.entities.Dealer;
import cz.cvut.fit.mi_mdw.entities.Order;
import cz.cvut.fit.mi_mdw.util.PMF;
import java.util.List;
import javax.jdo.Query;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/orders")
public class OrdersWS {

    @GET
    @Path("/{dealerID}")
    @Produces("application/xml")
    public List<Order> get(@PathParam("dealerID") String dealerID) {
        Query query = PMF.getPM().newQuery(Order.class);

        Dealer dealer = PMF.getPM().getObjectById(Dealer.class, Long.valueOf(dealerID));

        query.setFilter("dealer == searchDealer");
        query.declareParameters("cz.cvut.fit.mi_mdw.entities.Dealer searchDealer");

        return (List<Order>) query.execute(dealer);
    }
}
