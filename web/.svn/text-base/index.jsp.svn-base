<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.util.List"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="cz.cvut.fit.mi_mdw.util.Helpers"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="htmlHead.jsp" %>

<title>Car Bazaar</title>

<%@include file="header.jsp" %>



<div id="quote">
    <cite>
        Hi! Welcome to <strong>Car Bazaar</strong>! The largest fast growing car database and web
        service application in the world. Choose from thousands of vehicles!
    </cite>
</div>

<div class="clearfix">
    <%
                List<Car> cars = (List<Car>) request.getAttribute("cars");
                int i = 0;
                for (Car car : cars) {
    %>
    <div class="box car<% if (i % 2 != 0) {%> even <% }%>">
        <img src="css/images/sample_car.jpg" alt="Skoda Octavia combi" title="Skoda Octavia combi" />
        <h3><%= String.format("%s %s", car.getModel().getBrand().getName(), car.getModel().getName())%></h3>
        <table cellpadding="0" cellspacing="0">
            <tr>
                <th>engine:</th>
                <td><%= car.getEngine().toString()%></td>
            </tr>
            <tr>
                <th>fuel:</th>
                <td><%= car.getFuel().toString()%></td>
            </tr>
            <tr>
                <th>power:</th>
                <td><%= car.getPower().toString()%> kW</td>
            </tr>
            <tr>
                <th>consumption:</th>
                <td><%= car.getConsumption().setScale(1, RoundingMode.HALF_EVEN).toPlainString()%> l/100km</td>
            </tr>
        </table>
        <div class="price"><p><%= car.getPrice().toPlainString()%>,-</p></div>
        <a href="/?action=buy&id=<%= String.format("%s-%s-%s", car.getModel().getBrand().getID().getId(), car.getModel().getID().getId(), car.getID().getId())%>" class="buy" id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>" title="Buy this car.">Buy</a>
        <a href="/?action=ask&id=<%= String.format("%s-%s-%s", car.getModel().getBrand().getID().getId(), car.getModel().getID().getId(), car.getID().getId())%>" class="ask" id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>" title="Ask the dealer about this car.">Ask</a>
    </div>
    <%
                    i++;
                }
    %>
</div>

<%
            for (Car car : cars) {
%>
<div class="BuyFormDiv-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%> Dialog">
    <form method="post" action="/?action=buy" class="BuyForm-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>" name="BuyForm">
        <input type="hidden" id="BuyCarID" name="BuyCarID" value="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"/>
        <label for="BuyUsername">Username</label>
        <input type="text" id="BuyUsername" name="BuyUsername"/>
        <label for="BuyPassword">Password</label>
        <input type="text" id="BuyPassword" name="BuyPassword"/>
    </form>
</div>
<%
            }
%>

<%
            for (Car car : cars) {
%>
<div class="AskFormDiv-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%> Dialog">
    <form method="post" action="/?action=ask" class="AskForm-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>" name="AskForm">
        <input type="hidden" id="AskCarID" name="AskCarID" value="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"/>
        <label for="AskText">Question</label>
        <textarea cols="40" rows="8" id="AskText" name="AskText"></textarea>
    </form>
</div>
<%
            }
%>

<h2>Cars from fit-mdw-ws10-103-2 store:</h2>

<%

            NodeList listOfCars = (NodeList) request.getAttribute("listOfCars");
            int j = 0;

            try {
                for (i = 0; i < listOfCars.getLength(); i++) {

                    Node carNode = listOfCars.item(i);
                    if (carNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element carElement = (Element) carNode;

                        // Car name
                        NodeList manList = carElement.getElementsByTagName("name");
                        Element manElement = (Element) manList.item(0);
                        NodeList textManLibs = manElement.getChildNodes();

                        // Types
                        NodeList typeList = carElement.getElementsByTagName("type");
                        Element typeElement = (Element) typeList.item(0);
                        NodeList textTypeLibs = typeElement.getChildNodes();

                        // Color
                        NodeList colorList = carElement.getElementsByTagName("color");
                        Element colorElement = (Element) colorList.item(0);
                        NodeList textColorLibs = colorElement.getChildNodes();

                        // Price
                        NodeList priceList = carElement.getElementsByTagName("price");
                        Element priceElement = (Element) priceList.item(0);
                        NodeList textPriceLibs = priceElement.getChildNodes();

                        // Volumes
                        NodeList volumeList = carElement.getElementsByTagName("volume");
                        Element volumeElement = (Element) volumeList.item(0);
                        NodeList textVolumeLibs = volumeElement.getChildNodes();

                        // Power
                        NodeList powerList = carElement.getElementsByTagName("power");
                        Element powerElement = (Element) powerList.item(0);
                        NodeList textPowerLibs = powerElement.getChildNodes();

%>
<div class="box external car<% if (j % 2 != 0) {%> even <% }%>">
    <img src="css/images/sample_car.jpg" alt="Skoda Octavia combi" title="Skoda Octavia combi" />
    <h3><%= ((Node) textManLibs.item(0)).getNodeValue().trim()%> <%= ((Node) textTypeLibs.item(0)).getNodeValue().trim()%></h3>
    <table cellpadding="0" cellspacing="0">
        <tr>
            <th>color:</th>
            <td><%= ((Node) textColorLibs.item(0)).getNodeValue().trim()%></td>
        </tr>
        <tr>
            <th>volume:</th>
            <td><%= ((Node) textVolumeLibs.item(0)).getNodeValue().trim()%></td>
        </tr>
        <tr>
            <th>power:</th>
            <td><%= ((Node) textPowerLibs.item(0)).getNodeValue().trim()%> kW</td>
        </tr>

    </table>
    <div class="price"><p><%= ((Node) textPriceLibs.item(0)).getNodeValue().trim()%> ,-</p></div>
</div>
<%
                    }
                    j++;
                }
            } catch (NullPointerException e) {
            }
%>

<%@include file="footer.jsp" %>
