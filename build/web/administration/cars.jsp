<%@page import="java.math.RoundingMode"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Dealer"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Model"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Engine"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Fuel"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Cars</title>

<%@include file="header.jsp" %>

<ul class="submenu clearfix">
    <li><a href="#" title="Add car" id="AddCarButton">Add car</a></li>
</ul>

<h2>Cars</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Dealer</th>
            <th>Model</th>
            <th>Engine</th>
            <th>Fuel</th>
            <th>Power</th>
            <th>Consumption</th>
            <th>Price</th>
            <th>Description</th>
            <!-- <th>Edit</th> -->
            <th class="delete">Advertise</th>
            <th class="delete">Delete</th>
        </tr>
    </thead>
    <tbody>
        <%
                List<Car> cars = (List<Car>) request.getAttribute("cars");
                int i = 0;
                for (Car car : cars) {
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= car.getID().getId()%></td>
            <td><%= car.getDealer().toString()%></td>
            <td><%= car.getModel().getName()%></td>
            <td><%= car.getEngine().toString()%></td>
            <td><%= car.getFuel().toString()%></td>
            <td><%= car.getPower().toString()%></td>
            <td><%= car.getConsumption().setScale(1, RoundingMode.HALF_EVEN).toPlainString()%></td>
            <td><%= car.getPrice().toPlainString()%></td>
            <td><%= car.getDescription()%></td>
            <!-- <td><a href="#" title="Edit" class="EditCarButton" id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-wrench"></span></div></a></td> -->
            <td align="center"><a href="#" title="Advertise" class="AdvertiseCarButton" id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-plusthick"></span></div></a></td>
            <td align="center"><a href="#" title="Delete" class="DeleteCarButton" id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-trash"></span></div></a></td>
        </tr>
        <%
                    i++;
                }
        %>
    </tbody>
</table>

<div id="AddCarFormDiv" class="Dialog">
    <form method="post" action="/admin?action=addCar" id="AddCarForm" name="AddCarForm">
        <label for="AddCarDealer">Dealer</label>
        <select id="AddCarDealer" name="AddCarDealer">
            <%
                    List<Dealer> dealers = (List<Dealer>) request.getAttribute("dealers");
                    for (Dealer dealer : dealers) {
                        out.print(String.format("<option value=\"%s\">%s</option>", dealer.getID().getId(), dealer.getName()));
                    }
            %>
        </select><br/>
        <label for="AddCarModel">Model</label>
        <select id="AddCarModel" name="AddCarModel">
            <%
                    List<Model> models = (List<Model>) request.getAttribute("models");
                    for (Model model : models) {
                        out.print(String.format("<option value=\"%s-%s\">%s</option>", model.getBrand().getID().getId(), model.getID().getId(), model.getName()));
                    }
            %>
        </select><br/>
        <label for="AddCarEngine">Engine</label>
        <select id="AddCarEngine" name="AddCarEngine">
            <%
                    for (Engine e : Engine.values()) {
                        out.print(String.format("<option value=\"%s\">%s</option>", e.toString(), e.toString()));
                    }
            %>
        </select><br/>
        <label for="AddCarFuel">Fuel</label>
        <select id="AddCarFuel" name="AddCarFuel">
            <%
                    for (Fuel f : Fuel.values()) {
                        out.print(String.format("<option value=\"%s\">%s</option>", f.toString(), f.toString()));
                    }
            %>
        </select><br/>
        <label for="AddCarPrice">Price</label>
        <input type="text" id="AddCarPrice" name="AddCarPrice"/>
        <label for="AddCarPower">Power</label>
        <input type="text" id="AddCarPower" name="AddCarPower"/>
        <label for="AddCarConsumption">Consumption</label>
        <input type="text" id="AddCarConsumption" name="AddCarConsumption"/>
        <label for="AddCarDescription">Description</label>
        <input type="text" id="AddCarDescription" name="AddCarDescription"/>
    </form>
</div>

<%
        for (Car car : cars) {
%>
<div class="EditCarFormDiv-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%> Dialog">
    <form method="post" action="/admin?action=editCar" id="EditCarForm-<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>" name="EditCarForm">
        <input type="hidden" id="EditCarID" name="EditCarID" value="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>"/>
        <label for="EditCarDealer">Dealer</label>
        <select id="EditCarDealer" name="EditCarDealer">
            <%
                            for (Dealer dealer : dealers) {
                                out.print(String.format("<option value=\"%s\">%s</option>", dealer.getID().getId(), dealer.getName()));
                            }
            %>
        </select><br/>
        <label for="EditCarModel">Model</label>
        <select id="EditCarModel" name="EditCarModel">
            <%
                            for (Model model : models) {
                                out.print(String.format("<option value=\"%s-%s\">%s</option>", model.getBrand().getID().getId(), model.getID().getId(), model.getName()));
                            }
            %>
        </select><br/>
        <label for="EditCarEngine">Engine</label>
        <select id="EditCarEngine" name="EditCarEngine">
            <%
                            for (Engine e : Engine.values()) {
                                out.print(String.format("<option value=\"%s\">%s</option>", e.toString(), e.toString()));
                            }
            %>
        </select><br/>
        <label for="EditCarFuel">Fuel</label>
        <select id="EditCarFuel" name="EditCarFuel">
            <%
                            for (Fuel f : Fuel.values()) {
                                out.print(String.format("<option value=\"%s\">%s</option>", f.toString(), f.toString()));
                            }
            %>
        </select><br/>
        <label for="EditCarPrice">Price</label>
        <input type="text" id="EditCarPrice" name="EditCarPrice" value="<%= car.getPrice()%>"/>
        <label for="EditCarPower">Power</label>
        <input type="text" id="EditCarPower" name="EditCarPower" value="<%= car.getPower()%>"/>
        <label for="EditCarConsumption">Consumption</label>
        <input type="text" id="EditCarConsumption" name="EditCarConsumption" value="<%= car.getConsumption()%>"/>
        <label for="EditCarDescription">Description</label>
        <input type="text" id="EditCarDescription" name="EditCarDescription" value="<%= car.getDescription()%>"/>
    </form>
</div>
<%        }
%>

<div id="ConfirmationDialog" class="Dialog">
</div>

<%@include file="footer.jsp" %>
