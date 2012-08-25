package cz.cvut.fit.mi_mdw.servlets;

import cz.cvut.fit.mi_mdw.entities.Car;
import cz.cvut.fit.mi_mdw.entities.Dealer;
import cz.cvut.fit.mi_mdw.entities.Engine;
import cz.cvut.fit.mi_mdw.entities.Fuel;
import cz.cvut.fit.mi_mdw.entities.Model;
import cz.cvut.fit.mi_mdw.entities.Order;
import cz.cvut.fit.mi_mdw.entities.User;
import cz.cvut.fit.mi_mdw.util.Message;
import static cz.cvut.fit.mi_mdw.util.PMF.*;
import cz.cvut.fit.mi_mdw.util.Util;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.URLConnectionClientExecutor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FrontendServlet extends HttpServlet {

    public static final String EMPTY_VALUE = "choose";
    public static final String MAX_VALUE = "inf";
    private static final long serialVersionUID = 4L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private List<Message> messages = new ArrayList<Message>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        String forwardPage = action(request.getParameter("action"));
        request.setAttribute("Messages", messages);
        request.getRequestDispatcher("/" + forwardPage).forward(request, response);
    }

    private void index() {
        PersistenceManager pm = getPM();

        Query carsQuery = pm.newQuery(Car.class);
        request.setAttribute("cars", carsQuery.execute());

        ClientRequest restRequest = new ClientRequest("http://fit-mdw-ws10-103-2.appspot.com/ws/cars", new URLConnectionClientExecutor());
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

                NodeList listOfCars = doc.getElementsByTagName("car");

                request.setAttribute("listOfCars", listOfCars);

            } else {
                messages.add(Message.create("Store fit-mdw-ws10-103-2 not available.  Try again later."));
            }
        } catch (Exception e) {
             messages.add(Message.create("Store fit-mdw-ws10-103-2 not available. Try again later."));
        }



    }

    private void search() {
        PersistenceManager pm = getPM();

        Query modelsQuery = pm.newQuery(Model.class);
        request.setAttribute("models", modelsQuery.execute());

        if ("searching".equals(request.getParameter("Searching"))) {
            List<List<Car>> allFilteredCars = new ArrayList<List<Car>>();

            String searchModel = request.getParameter("SearchModel");
            if (!FrontendServlet.EMPTY_VALUE.equals(searchModel)) {
                Query query = pm.newQuery(Car.class);

                Model model = pm.getObjectById(Model.class, Util.modelKeyFromString(searchModel));

                query.setFilter("model == searchModel");
                query.declareParameters("cz.cvut.fit.mi_mdw.entities.Model searchModel");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(model)));
            }

            String searchEngine = request.getParameter("SearchEngine");
            if (!FrontendServlet.EMPTY_VALUE.equals(searchEngine)) {
                Query query = pm.newQuery(Car.class);

                Engine engine = Engine.valueOf(searchEngine);

                query.setFilter("engine == searchEngine");
                query.declareParameters("cz.cvut.fit.mi_mdw.entities.Engine searchEngine");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(engine)));
            }

            String searchFuel = request.getParameter("SearchFuel");
            if (!FrontendServlet.EMPTY_VALUE.equals(searchFuel)) {
                Query query = pm.newQuery(Car.class);

                Fuel fuel = Fuel.valueOf(searchFuel);

                query.setFilter("fuel == searchFuel");
                query.declareParameters("cz.cvut.fit.mi_mdw.entities.Fuel searchFuel");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(fuel)));
            }

            String searchPrice = request.getParameter("SearchPrice");
            if (!FrontendServlet.EMPTY_VALUE.equals(searchPrice)) {
                Query query = pm.newQuery(Car.class);

                String[] minMaxPrice = searchPrice.split("-");
                BigDecimal minPrice = new BigDecimal(minMaxPrice[0]);
                BigDecimal maxPrice = minMaxPrice[1].equals(FrontendServlet.MAX_VALUE) ? new BigDecimal(Integer.MAX_VALUE) : new BigDecimal(minMaxPrice[1]);

                query.setFilter("price >= minPrice && price <= maxPrice");
                query.declareParameters("java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(minPrice, maxPrice)));
            }

            String searchPower = request.getParameter("SearchPower");
            if (!FrontendServlet.EMPTY_VALUE.equals(searchPower)) {
                Query query = pm.newQuery(Car.class);

                String[] minMaxPower = searchPower.split("-");
                int minPower = Integer.valueOf(minMaxPower[0]);
                int maxPower = minMaxPower[1].equals(FrontendServlet.MAX_VALUE) ? Integer.MAX_VALUE : Integer.valueOf(minMaxPower[1]);

                query.setFilter("power >= minPower && power <= maxPower");
                query.declareParameters("int minPower, Integer maxPower");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(minPower, maxPower)));
            }

            String searchConsumption = request.getParameter("SearchConsumption");
            if (!"".equals(searchConsumption)) {
                Query query = pm.newQuery(Car.class);

                BigDecimal maxConsumption = new BigDecimal(searchConsumption);

                query.setFilter("consumption <= maxConsumption");
                query.declareParameters("java.math.BigDecimal maxConsumption");

                allFilteredCars.add(new ArrayList<Car>((List<Car>) query.execute(maxConsumption)));
            }

            Set<Car> cars = new HashSet<Car>();
            for (List<Car> c : allFilteredCars) {
                if (cars.isEmpty()) {
                    cars.addAll(c);
                } else {
                    cars.retainAll(c);
                }
            }

            request.setAttribute("cars", new ArrayList<Car>(cars));
        } else {
            request.setAttribute("cars", new ArrayList<Car>());
        }
    }

    private void buy() {
        PersistenceManager pm = getPM();

        Car car = pm.getObjectById(Car.class, Util.carKeyFromString(request.getParameter("BuyCarID")));
        Dealer dealer = pm.getObjectById(Dealer.class, car.getDealer());

        Query userQuery = pm.newQuery(User.class);

        userQuery.setFilter("username == user && password == pass");
        userQuery.declareParameters("String user, String pass");

        List<User> users = (List<User>) userQuery.execute(request.getParameter("BuyUsername"), request.getParameter("BuyPassword"));

        Query orderQuery = pm.newQuery(Order.class);

        orderQuery.setFilter("car == wanted");
        orderQuery.declareParameters("com.google.appengine.api.datastore.Key wanted");

        List<Order> carBought = (List<Order>) orderQuery.execute(car.getID());

        if (users.size() == 1 && carBought.isEmpty()) {
            User user = users.get(0);

            Order order = new Order(new Date(), car.getID(), dealer, user.getID());
            dealer.getOrders().add(order);
            pm.makePersistent(order);
            user.getOrders().add(order.getID());

            // Send and email
//            Properties props = new Properties();
//            Session session = Session.getDefaultInstance(props, null);
//            String msgBody = request.getParameter("AskText");
//            try {
//                javax.mail.Message msg = new MimeMessage(session);
//                msg.setFrom(new InternetAddress("janecek.jakub@gmail.com"));
//                msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(dealer.getEmail()));
//                msg.setSubject("Your car was bought.");
//                msg.setText(msgBody);
//                Transport.send(msg);
//
//                messages.add(Message.create("Question sent."));
//            } catch (Exception ex) {
//                messages.add(Message.create("Your question could not be sent. Please, try again."));
//            }


            messages.add(Message.create("Congratulations! You have successfully bought your new car."));
            pm.close();
        } else {
            if (carBought.size() > 0) {
                messages.add(Message.create("Sorry, but you cannot buy this car since it has already been sold."));
            } else {
                messages.add(Message.create("Incorrect username and password combination."));
            }
        }
    }

    private void ask() {
        PersistenceManager pm = getPM();

        Car car = pm.getObjectById(Car.class, Util.carKeyFromString(request.getParameter("AskCarID")));
        Dealer dealer = pm.getObjectById(Dealer.class, car.getDealer());

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = request.getParameter("AskText");

        try {
            javax.mail.Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("janecek.jakub@gmail.com"));
            msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(dealer.getEmail()));
            msg.setSubject("Question about your car");
            msg.setText(msgBody);
            Transport.send(msg);

            messages.add(Message.create("Question sent."));
        } catch (Exception ex) {
            messages.add(Message.create("Your question could not be sent. Please, try again."));
        }
    }

    private String action(String action) {
        if (action == null) {
            index();
            return "index.jsp";
        }

        if (action.equals("search")) {
            search();
            return "search.jsp";
        } else if (action.equals("buy")) {
            buy();
            return "ajax.jsp";
        } else if (action.equals("ask")) {
            ask();
            return "ajax.jsp";
        }

        index();
        return "index.jsp";
    }

    private String printNode(Node node) {
        String xmlString = "";
        try {
            // Set up the output transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            // Print the DOM node
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(node);
            trans.transform(source, result);
            xmlString = sw.toString();

        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xmlString;

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
        return "Frontend servlet.";
    }// </editor-fold>
}
