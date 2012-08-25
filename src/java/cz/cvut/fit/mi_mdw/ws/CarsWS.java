package cz.cvut.fit.mi_mdw.ws;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import cz.cvut.fit.mi_mdw.entities.Brand;
import cz.cvut.fit.mi_mdw.entities.Car;
import cz.cvut.fit.mi_mdw.entities.Dealer;
import cz.cvut.fit.mi_mdw.entities.Engine;
import cz.cvut.fit.mi_mdw.entities.Fuel;
import cz.cvut.fit.mi_mdw.entities.Model;
import cz.cvut.fit.mi_mdw.util.PMF;
import cz.cvut.fit.mi_mdw.util.Util;
import java.util.List;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/cars")
public class CarsWS {

    @GET
    @Path("/")
    @Produces("application/xml")
    public List<Car> getAllCars() {
        return (List<Car>) PMF.getPM().newQuery(Car.class).execute();
    }

    @POST
    @Path("/")
    @Produces("application/xml")
    public Result addCar(
            @FormParam("brand") String brand,
            @FormParam("model") String model,
            @FormParam("engine") String engine,
            @FormParam("fuel") String fuel,
            @FormParam("price") String price,
            @FormParam("power") String power,
            @FormParam("consumption") String consumption,
            @FormParam("description") String description,
            @FormParam("dealer") String dealer) {

        try {
            PersistenceManager pm = PMF.getPM();

            Key dealerKey = KeyFactory.createKey(Dealer.class.getSimpleName(), Long.valueOf(dealer));
            Key brandKey = KeyFactory.createKey(Dealer.class.getSimpleName(), Long.valueOf(brand));
            Key modelKey = KeyFactory.createKey(Dealer.class.getSimpleName(), Long.valueOf(model));
            Model carModel = pm.getObjectById(Model.class, KeyFactory.createKey(KeyFactory.createKey(Brand.class.getSimpleName(), brandKey.getId()), Model.class.getSimpleName(), modelKey.getId()));

            Car car = new Car(carModel, Engine.valueOf(engine), Fuel.valueOf(fuel), price, power, consumption, description, dealerKey);

            Set<Car> cars = carModel.getCars();
            cars.add(car);
            carModel.setCars(cars);

            Dealer d = pm.getObjectById(Dealer.class, dealerKey.getId());
            d.addCar(car);

            pm.close();
            Result r = new Result(true);
            r.setValue(String.format("%d-%d-%d", brandKey.getId(), modelKey.getId(), car.getID().getId()));
            return r;
        }
        catch (Exception ex) {
        }
        return new Result(false);
    }

    @DELETE
    @Path("/{carID}")
    @Produces("application/xml")
    public Result deleteCar(@PathParam("carID") String carID) {
        try {
            PersistenceManager pm = PMF.getPM();

            Car car = pm.getObjectById(Car.class, Util.carKeyFromString(carID));
            Dealer dealer = pm.getObjectById(Dealer.class, car.getDealer());
            dealer.getCars().remove(car.getID());
            pm.deletePersistent(car);
            return new Result(true);
        }
        catch (Exception ex) {
            return new Result(false);
        }
    }

    @GET
    @Path("/brands")
    @Produces("application/xml")
    public List<Brand> getAllBrands() {
        return (List<Brand>) PMF.getPM().newQuery(Brand.class).execute();
    }

    @GET
    @Path("/models")
    @Produces("application/xml")
    public List<Model> getAllModels() {
        return (List<Model>) PMF.getPM().newQuery(Model.class).execute();
    }
}
