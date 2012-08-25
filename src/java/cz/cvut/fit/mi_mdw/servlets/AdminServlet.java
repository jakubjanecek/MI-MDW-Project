package cz.cvut.fit.mi_mdw.servlets;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.cvut.fit.mi_mdw.util.PMF;
import cz.cvut.fit.mi_mdw.entities.Brand;
import cz.cvut.fit.mi_mdw.entities.Car;
import cz.cvut.fit.mi_mdw.entities.Dealer;
import cz.cvut.fit.mi_mdw.entities.Engine;
import cz.cvut.fit.mi_mdw.entities.Fuel;
import cz.cvut.fit.mi_mdw.util.Message;
import cz.cvut.fit.mi_mdw.entities.Model;
import cz.cvut.fit.mi_mdw.entities.Order;
import cz.cvut.fit.mi_mdw.entities.User;
import cz.cvut.fit.mi_mdw.util.ReceivedOrderDelete;
import cz.cvut.fit.mi_mdw.util.ResponseConverter;
import cz.cvut.fit.mi_mdw.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.URLConnectionClientExecutor;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 3L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService = UserServiceFactory.getUserService();
    private List<Message> messages = new ArrayList<Message>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        authenticate();
        String forwardPage = action(request.getParameter("action"));
        request.setAttribute("Messages", messages);
        request.getRequestDispatcher("/administration/" + forwardPage).forward(request, response);
    }

    private void cars() {
        PersistenceManager pm = PMF.getPM();

        Query carsQuery = pm.newQuery(Car.class);
        request.setAttribute("cars", carsQuery.execute());

        Query dealerQuery = pm.newQuery(Dealer.class);
        request.setAttribute("dealers", dealerQuery.execute());

        Query modelsQuery = pm.newQuery(Model.class);
        request.setAttribute("models", modelsQuery.execute());
    }

    private void dealers() {
        Query dealerQuery = PMF.getPM().newQuery(Dealer.class);
        request.setAttribute("dealers", dealerQuery.execute());
    }

    private void brands() {
        Query brandsQuery = PMF.getPM().newQuery(Brand.class);
        request.setAttribute("brands", brandsQuery.execute());
    }

    private void addCar() {
        PersistenceManager pm = PMF.getPM();

        Key dealer = KeyFactory.createKey(Dealer.class.getSimpleName(), Long.valueOf(request.getParameter("AddCarDealer")));
        String modelStringID = request.getParameter("AddCarModel");
        long brandID = Long.valueOf(modelStringID.split("-")[0]);
        long modelID = Long.valueOf(modelStringID.split("-")[1]);
        Model model = pm.getObjectById(Model.class, KeyFactory.createKey(KeyFactory.createKey(Brand.class.getSimpleName(), brandID), Model.class.getSimpleName(), modelID));
        Engine engine = Engine.valueOf(request.getParameter("AddCarEngine"));
        Fuel fuel = Fuel.valueOf(request.getParameter("AddCarFuel"));
        String price = request.getParameter("AddCarPrice");
        String power = request.getParameter("AddCarPower");
        String consumption = request.getParameter("AddCarConsumption");
        String description = request.getParameter("AddCarDescription");

        try {
            Car car = new Car(model, engine, fuel, price, power, consumption, description, dealer);

            Set<Car> cars = model.getCars();
            cars.add(car);
            model.setCars(cars);

            Dealer d = pm.getObjectById(Dealer.class, dealer);
            d.addCar(car);

            pm.close();
            messages.add(Message.create("Car added."));
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Car could not be added. Some form fields were incorrect."));
        }
    }

    private void autosalonOrders() {
        // Password and username static for testing
        String username = "Macejkou";
        String password = "1234567890";

        ClientRequest restRequest = new ClientRequest("http://fit-mdw-ws10-103-2.appspot.com/ws/orders?name=" + username + "&passwd=" + password, new URLConnectionClientExecutor());
        restRequest.accept("application/xml");

        try {
            ClientResponse<String> restResponse = restRequest.get(String.class);
            restResponse.releaseConnection();

            if (restResponse.getStatus() == 200) {
                String xml = restResponse.getEntity();
                InputSource is = new InputSource(new StringReader(xml));

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(false); // never forget this!
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document doc = builder.parse(is);

                doc.getDocumentElement().normalize();

                NodeList listOfOrders = doc.getElementsByTagName("order");

                request.setAttribute("listOfOrders", listOfOrders);

            } else {
                messages.add(Message.create("Store fit-mdw-ws10-103-2 not available.  Try again later."));
            }
        } catch (Exception e) {
            messages.add(Message.create("Store fit-mdw-ws10-103-2 not available. Try again later."));
        }
    }

    private void deleteAutosalonOrder() {
        try {
            //ReceivedOrderConverter o = new ReceivedOrderConverter("manUser", "manUser", cars);
            ReceivedOrderDelete o = new ReceivedOrderDelete("Macejkou", "1234567890", request.getParameter("id"));


            // JAXBContext context = JAXBContext.newInstance(ReceivedOrderConverter.class);
            JAXBContext context = JAXBContext.newInstance(ReceivedOrderDelete.class);
            URL url = new URL("http://fit-mdw-ws10-103-2.appspot.com/ws/orders");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");

            OutputStream os = connection.getOutputStream();
            context.createMarshaller().marshal(o, os);
            os.flush();

            connection.getResponseCode();
            InputStream is = connection.getInputStream();
            JAXBContext responseContext = JAXBContext.newInstance(ResponseConverter.class);
            ResponseConverter c = (ResponseConverter) responseContext.createUnmarshaller().unmarshal(is);

            switch (c.getState()) {
                case 0:
                    messages.add(Message.create("Order was succesfully deleted."));
                    break;
                case 1:
                    messages.add(Message.create("Order cannot be deleted. Its solved already."));
                    break;
                case 2:
                    messages.add(Message.create("Order cannot be deleted. Wrong credentials."));
                    break;
            }




            connection.disconnect();
        } catch (Exception e) {
            messages.add(Message.create(e.getMessage()));
        }

    }

    private void deleteCar() {
        PersistenceManager pm = PMF.getPM();

        Car car = pm.getObjectById(Car.class, Util.carKeyFromString(request.getParameter("id")));
        Dealer dealer = pm.getObjectById(Dealer.class, car.getDealer());
        dealer.getCars().remove(car.getID());
        pm.deletePersistent(car);
        pm.close();
        messages.add(Message.create("Car deleted."));
    }

    private void advertiseCar() {
        PersistenceManager pm = PMF.getPM();

        Car car = pm.getObjectById(Car.class, Util.carKeyFromString(request.getParameter("id")));
        Dealer dealer = pm.getObjectById(Dealer.class, car.getDealer());

        String title = String.format("%s %s", car.getModel().getBrand().getName(), car.getModel().getName());
        String description = String.format("Prodejce: %s, Motor: %s, Palivo: %s, Výkon: %s, Cena: %s", dealer.getName(), car.getEngine().toString(), car.getFuel().toString(), car.getPower(), car.getPrice());

        ClientRequest restRequest = new ClientRequest("http://fit-mdw-ws10-102-6.appspot.com/rest/addAd/", new URLConnectionClientExecutor());
        restRequest.body("application/xml", String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><advert><title>%s</title><description>%s</description><category>Auta</category><locality>Støední Èechy</locality></advert>", title, description));

        try {
            ClientResponse<String> restResponse = restRequest.post(String.class);
            restResponse.releaseConnection();

            if (restResponse.getStatus() == 200) {
                messages.add(Message.create("Car advertised."));
            } else {
                messages.add(Message.create("Car could not be advertised."));
            }
        } catch (Exception ex) {
            // for some reason app engine requests time out even though they are fine
            messages.add(Message.create("Car advertised."));
        }
    }

    private void addDealer() {
        String name = request.getParameter("AddDealerName");
        String address = request.getParameter("AddDealerAddress");
        String email = request.getParameter("AddDealerEmail");
        String phone = request.getParameter("AddDealerPhone");
        try {
            Dealer dealer = new Dealer(name, address, email, phone, Collections.<Key>emptySet(), Collections.<Order>emptySet());
            PMF.getPM().makePersistent(dealer);
            messages.add(Message.create("Dealer added."));
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Dealer could not be added. Some form fields were incorrect."));
        }
    }

    private void editDealer() {
        String id = request.getParameter("EditDealerID");
        String name = request.getParameter("EditDealerName");
        String address = request.getParameter("EditDealerAddress");
        String email = request.getParameter("EditDealerEmail");
        String phone = request.getParameter("EditDealerPhone");

        PersistenceManager pm = PMF.getPM();

        try {
            Dealer dealer = pm.getObjectById(Dealer.class, Long.valueOf(id));

            dealer.setName(name);
            dealer.setAddress(address);
            dealer.setEmail(email);
            dealer.setPhone(phone);
            messages.add(Message.create("Dealer edited."));
            pm.close();
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Dealer could not be edited. Some form fields were incorrect."));
        }
    }

    private void deleteDealer() {
        String id = request.getParameter("id");
        PersistenceManager pm = PMF.getPM();
        pm.deletePersistent(pm.getObjectById(Dealer.class, Long.valueOf(id)));
        messages.add(Message.create("Dealer deleted."));
    }

    private void addBrand() {
        String name = request.getParameter("AddBrandName");
        String desc = request.getParameter("AddBrandDescription");
        try {
            Brand brand = new Brand(name, desc, Collections.<Model>emptySet());
            PMF.getPM().makePersistent(brand);
            messages.add(Message.create("Brand added."));
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Brand could not be added. Some form fields were incorrect."));
        }
    }

    private void editBrand() {
        String id = request.getParameter("EditBrandID");
        String name = request.getParameter("EditBrandName");
        String desc = request.getParameter("EditBrandDescription");

        PersistenceManager pm = PMF.getPM();

        try {
            Brand brand = pm.getObjectById(Brand.class, Long.valueOf(id));

            brand.setName(name);
            brand.setDescription(desc);
            messages.add(Message.create("Brand edited."));
            pm.close();
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Brand could not be edited. Some form fields were incorrect."));
        }
    }

    private void deleteBrand() {
        String id = request.getParameter("id");
        PersistenceManager pm = PMF.getPM();
        pm.deletePersistent(pm.getObjectById(Brand.class, Long.valueOf(id)));
        messages.add(Message.create("Brand deleted."));
    }

    private void addModel() {
        String brandID = request.getParameter("AddModelBrand");
        String name = request.getParameter("AddModelName");
        String desc = request.getParameter("AddModelDescription");

        PersistenceManager pm = PMF.getPM();
        try {
            Brand brand = pm.getObjectById(Brand.class, Long.valueOf(brandID));
            brand.addModel(new Model(name, desc, brand, Collections.<Car>emptySet()));
            pm.close();
            messages.add(Message.create("Model added."));
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("Model could not be added. Some form fields were incorrect."));
        }
    }

    private void users() {
        Query usersQuery = PMF.getPM().newQuery(User.class);
        request.setAttribute("users", usersQuery.execute());
    }

    private void addUser() {
        String name = request.getParameter("AddUserName");
        String username = request.getParameter("AddUserUsername");
        String password = request.getParameter("AddUserPassword");
        String address = request.getParameter("AddUserAddress");
        String email = request.getParameter("AddUserEmail");

        PersistenceManager pm = PMF.getPM();
        try {
            User user = new User(username, password, name, address, email, new HashSet<Key>());
            pm.makePersistent(user);
            messages.add(Message.create("User added."));
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("User could not be added. Some form fields were incorrect."));
        }
    }

    private void editUser() {
        String id = request.getParameter("EditUserID");
        String name = request.getParameter("EditUserName");
        String username = request.getParameter("EditUserUsername");
        String password = request.getParameter("EditUserPassword");
        String address = request.getParameter("EditUserAddress");
        String email = request.getParameter("EditUserEmail");

        PersistenceManager pm = PMF.getPM();

        try {
            User user = pm.getObjectById(User.class, Long.valueOf(id));

            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setAddress(address);
            user.setEmail(email);
            messages.add(Message.create("User edited."));
            pm.close();
        } catch (IllegalArgumentException ex) {
            messages.add(Message.create("User could not be edited. Some form fields were incorrect."));
        }
    }

    private void deleteUser() {
        String id = request.getParameter("id");
        PersistenceManager pm = PMF.getPM();
        pm.deletePersistent(pm.getObjectById(User.class, Long.valueOf(id)));
        messages.add(Message.create("User deleted."));
    }

    private void orders() {
        Query ordersQuery = PMF.getPM().newQuery(Order.class);
        request.setAttribute("orders", ordersQuery.execute());
    }

    private String action(String action) {
        if (action == null) {
            cars();
            return "cars.jsp";
        }

        if (action.equals("dealers")) {
            dealers();
            return "dealers.jsp";
        } else if (action.equals("brands")) {
            brands();
            return "brands.jsp";
        } else if (action.equals("autosalonOrders")) {
            autosalonOrders();
            return "autosalonOrders.jsp";
        } else if (action.equals("deleteAutosalonOrder")) {
            deleteAutosalonOrder();
            return "ajax.jsp";
        } else if (action.equals("addBrand")) {
            addBrand();
            return "ajax.jsp";
        } else if (action.equals("editBrand")) {
            editBrand();
            return "ajax.jsp";
        } else if (action.equals("deleteBrand")) {
            deleteBrand();
            return "ajax.jsp";
        } else if (action.equals("addModel")) {
            addModel();
            return "ajax.jsp";
        } else if (action.equals("addCar")) {
            addCar();
            return "ajax.jsp";
        } else if (action.equals("advertiseCar")) {
            advertiseCar();
            return "ajax.jsp";
        } else if (action.equals("deleteCar")) {
            deleteCar();
            return "ajax.jsp";
        } else if (action.equals("addDealer")) {
            addDealer();
            return "ajax.jsp";
        } else if (action.equals("editDealer")) {
            editDealer();
            return "ajax.jsp";
        } else if (action.equals("deleteDealer")) {
            deleteDealer();
            return "ajax.jsp";
        } else if (action.equals("users")) {
            users();
            return "users.jsp";
        } else if (action.equals("addUser")) {
            addUser();
            return "ajax.jsp";
        } else if (action.equals("editUser")) {
            editUser();
            return "ajax.jsp";
        } else if (action.equals("deleteUser")) {
            deleteUser();
            return "ajax.jsp";
        } else if (action.equals("orders")) {
            orders();
            return "orders.jsp";
        } else if (action.equals("testData")) {
            testData();
        }

        cars();
        return "cars.jsp";
    }

    private void authenticate() throws ServletException, IOException {
        if (!authenticated()) {
            response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
        }
    }

    private boolean authenticated() {
        return userService.getCurrentUser() != null;
    }

    private void testData() {
        PersistenceManager pm = PMF.getPM();

        // BRANDS and MODELS
        Set<Model> b1Models = new HashSet<Model>();
        Brand b1 = new Brand("Skoda", "Skoda Auto a.s.", b1Models);
        Model m1 = new Model("Fabia", "1.9 TDI", b1, Collections.<Car>emptySet());
        b1Models.add(m1);
        Model m2 = new Model("Fabia Combi", "1.6 TSI", b1, Collections.<Car>emptySet());
        b1Models.add(m2);
        Model m3 = new Model("Octavia Combi", "Tour 1.6 TDI", b1, Collections.<Car>emptySet());
        b1Models.add(m3);
        Model mi4 = new Model("Superb", "Classic 1.9 TDI", b1, Collections.<Car>emptySet());
        b1Models.add(mi4);

        pm.makePersistent(b1);

        Set<Model> b2Models = new HashSet<Model>();
        Brand b2 = new Brand("BMW", "BMW AG", b2Models);
        Model m4 = new Model("M3", "320", b2, Collections.<Car>emptySet());
        b2Models.add(m4);
        Model m5 = new Model("M3", "335", b2, Collections.<Car>emptySet());
        b2Models.add(m5);
        Model m6 = new Model("7 Series", "750", b2, Collections.<Car>emptySet());
        b2Models.add(m6);
        Model mi7 = new Model("X5", "550", b2, Collections.<Car>emptySet());
        b2Models.add(mi7);
        Model mi8 = new Model("Z1", "910", b2, Collections.<Car>emptySet());
        b2Models.add(mi8);
        pm.makePersistent(b2);

        Set<Model> b3Models = new HashSet<Model>();
        Brand b3 = new Brand("Ford", "Ford Motor Company", b3Models);
        Model m7 = new Model("Focus", "1.8T", b3, Collections.<Car>emptySet());
        b3Models.add(m7);
        Model m8 = new Model("Mondeo", "2.5T", b3, Collections.<Car>emptySet());
        b3Models.add(m8);
        Model m9 = new Model("Fusion", "hybrid", b3, Collections.<Car>emptySet());
        b3Models.add(m9);
        Model m10 = new Model("Taurus", "USA only", b3, Collections.<Car>emptySet());
        b3Models.add(m10);
        Model m11 = new Model("Fiesta", "1.2", b3, Collections.<Car>emptySet());
        b3Models.add(m11);
        pm.makePersistent(b3);


        Set<Model> b4Models = new HashSet<Model>();
        Brand b4 = new Brand("VW", "VolksWagen", b4Models);
        Model mi27 = new Model("Passat", "1.9 TDi", b4, Collections.<Car>emptySet());
        b4Models.add(mi27);
        Model mi28 = new Model("Jetta", "2.5 TDi", b4, Collections.<Car>emptySet());
        b4Models.add(mi28);
        Model mi9 = new Model("Sharan", "hybrid", b4, Collections.<Car>emptySet());
        b4Models.add(mi9);
        Model mi10 = new Model("Polo", "1.6TDi", b4, Collections.<Car>emptySet());
        b4Models.add(mi10);
        Model mi11 = new Model("Golf", "1.9TDi", b4, Collections.<Car>emptySet());
        b4Models.add(mi11);
        pm.makePersistent(b4);

        // DEALERS
        Dealer d1 = new Dealer("Dealer 1", "Dealer St. 123", "janecjak@fit.cvut.cz", "123456789", new HashSet<Key>(), new HashSet<Order>());
        pm.makePersistent(d1);

        Dealer d2 = new Dealer("Dealer 2", "Dealer St. 987", "janecjak@fit.cvut.cz", "987654321", new HashSet<Key>(), new HashSet<Order>());
        pm.makePersistent(d2);

        Dealer d3 = new Dealer("Dealer 3", "Thakurova 1", "szabofra@fit.cvut.cz", "987654321", new HashSet<Key>(), new HashSet<Order>());
        pm.makePersistent(d3);

        // CARS
        Car c1 = new Car(m1, Engine.INLINE, Fuel.OIL, "256000", "75", "6.4", "Reasonable family car.", d1.getID());
        pm.makePersistent(c1);
        m1.getCars().add(c1);
        Car c2 = new Car(m1, Engine.INLINE, Fuel.OIL, "195123", "75", "6.4", "Old but in good shape.", d1.getID());
        pm.makePersistent(c2);
        m1.getCars().add(c2);
        Car c3 = new Car(m5, Engine.V, Fuel.GAS, "563140", "198", "12.3", "Sports car.", d1.getID());
        pm.makePersistent(c3);
        m5.getCars().add(c3);
        Car c4 = new Car(m7, Engine.INLINE, Fuel.GAS, "399000", "110", "8.9", "Sports car for poor.", d1.getID());
        pm.makePersistent(c4);
        m7.getCars().add(c4);
        Car c5 = new Car(m11, Engine.INLINE, Fuel.GAS, "175123", "55", "6.5", "Car perfect for girls.", d1.getID());
        pm.makePersistent(c5);
        m11.getCars().add(c5);

        Car c6 = new Car(m3, Engine.V, Fuel.OIL, "396123", "85", "7.3", "4x4", d2.getID());
        pm.makePersistent(c6);
        m3.getCars().add(c6);
        Car c7 = new Car(m6, Engine.INLINE, Fuel.GAS, "1153236", "190", "14.4", "For managers.", d2.getID());
        pm.makePersistent(c7);
        m6.getCars().add(c7);
        Car c8 = new Car(m4, Engine.INLINE, Fuel.GAS, "680000", "210", "13.3", "Sports car.", d2.getID());
        pm.makePersistent(c8);
        m4.getCars().add(c8);
        Car c9 = new Car(m8, Engine.V, Fuel.GAS, "546123", "154", "11.9", "Excellent engine.", d2.getID());
        pm.makePersistent(c9);
        m8.getCars().add(c9);
        Car c10 = new Car(m2, Engine.INLINE, Fuel.LPG, "220000", "77", "10", "LPG", d2.getID());
        pm.makePersistent(c10);
        m2.getCars().add(c10);


        Car c11 = new Car(mi4, Engine.INLINE, Fuel.GAS, "210000", "74", "13.3", "Managers car for long distance", d3.getID());
        pm.makePersistent(c11);
        mi4.getCars().add(c11);
        Car c12 = new Car(mi4, Engine.INLINE, Fuel.LPG, "150000", "50", "6", "LPG car for managers", d3.getID());
        pm.makePersistent(c12);
        mi4.getCars().add(c12);
        Car c13 = new Car(mi9, Engine.INLINE, Fuel.GAS, "320000", "99", "12", "Super big vehicle for family", d2.getID());
        pm.makePersistent(c13);
        mi9.getCars().add(c13);
        Car c14 = new Car(mi11, Engine.INLINE, Fuel.GAS, "120000", "60", "7", "Sport car for young men", d2.getID());
        pm.makePersistent(c14);
        mi11.getCars().add(c14);
        Car c15 = new Car(mi10, Engine.V, Fuel.LPG, "120000", "60", "5", "Very cheap and inteligent car.", d2.getID());
        pm.makePersistent(c15);
        mi10.getCars().add(c15);

        pm.close();

        pm = PMF.getPM();

        d1 = pm.getObjectById(Dealer.class, d1.getID());
        d1.addCar(c1);
        d1.addCar(c2);
        d1.addCar(c3);
        d1.addCar(c4);
        d1.addCar(c5);

        d2 = pm.getObjectById(Dealer.class, d2.getID());
        d2.addCar(c6);
        d2.addCar(c7);
        d2.addCar(c8);
        d2.addCar(c9);
        d2.addCar(c10);

        // USERS
        User u1 = new User("testovaci", "testovaci", "Testovaci Testovaci", "Testovaci Street 123", "janecjak@fit.cvut.cz", new HashSet<Key>());
        pm.makePersistent(u1);

        pm.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Administration servlet.";
    }// </editor-fold>
}
