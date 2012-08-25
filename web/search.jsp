<%@page import="cz.cvut.fit.mi_mdw.servlets.FrontendServlet"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Model"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Engine"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Fuel"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.util.List"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar - Search</title>

<%@include file="header.jsp" %>

<div class="box search">
    <form method="post" action="/?action=search" id="SearchForm" name="SearchForm">
        <table>
            <input type="hidden" name="Searching" id="Searching" value="searching"/>
            <tr>
                <th>
                    <label for="SearchModel">Model</label>
                </th>
                <td>
                    <select id="SearchModel" name="SearchModel">
                        <option value="<%= FrontendServlet.EMPTY_VALUE%>">Choose&hellip;</option>
                        <%
                                    List<Model> models = (List<Model>) request.getAttribute("models");
                                    for (Model model : models) {
                                        out.print(String.format("<option value=\"%s-%s\">%s</option>", model.getBrand().getID().getId(), model.getID().getId(), model.getName()));
                                    }
                        %>
                    </select>
                </td>
            </tr>

            <tr>
                <th>
                    <label for="SearchEngine">Engine</label>
                </th>
                <td>
                    <select id="SearchEngine" name="SearchEngine">
                        <option value="<%= FrontendServlet.EMPTY_VALUE%>">Choose&hellip;</option>
                        <%
                                    for (Engine e : Engine.values()) {
                                        out.print(String.format("<option value=\"%s\">%s</option>", e.toString(), e.toString()));
                                    }
                        %>
                    </select>
                </td>
            </tr>

            <tr>
                <th>
                    <label for="SearchFuel">Fuel</label>
                </th>
                <td>

                    <select id="SearchFuel" name="SearchFuel">
                        <option value="<%= FrontendServlet.EMPTY_VALUE%>">Choose&hellip;</option>
                        <%
                                    for (Fuel f : Fuel.values()) {
                                        out.print(String.format("<option value=\"%s\">%s</option>", f.toString(), f.toString()));
                                    }
                        %>
                    </select>
                </td>
            </tr>

            <tr>
                <th>
                    <label for="SearchPrice">Price</label>
                </th>
                <td>
                    <select id="SearchPrice" name="SearchPrice">
                        <option value="<%= FrontendServlet.EMPTY_VALUE%>">Choose&hellip;</option>
                        <option value="0-50000">0 - 50 000</option>
                        <option value="50000-100000">50 000 - 100 000</option>
                        <option value="100000-200000">100 000 - 200 000</option>
                        <option value="200000-500000">200 000 - 500 000</option>
                        <option value="500000-<%= FrontendServlet.MAX_VALUE%>">500 000 and more</option>
                    </select>
                </td>
            </tr>

            <tr>
                <th>
                    <label for="SearchPower">Power</label>
                </th>
                <td>
                    <select id="SearchPower" name="SearchPower">
                        <option value="<%= FrontendServlet.EMPTY_VALUE%>">Choose&hellip;</option>
                        <option value="0-50">0 - 50</option>
                        <option value="50-100">50 - 100</option>
                        <option value="100-150">100 - 150</option>
                        <option value="150-200">150 - 200</option>
                        <option value="200-<%= FrontendServlet.MAX_VALUE%>">200 and more</option>
                    </select>
                </td>
            </tr>

            <tr>
                <th>
                  <label for="SearchConsumption">Max consumption</label>
                </th>
                <td>
                  <input type="text" id="SearchConsumption" name="SearchConsumption"/><br/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="reset" id="SearchReset" class="button" name="SearchReset" value="Reset"/>
                    <input type="submit" id="SearchSubmit" class="button" name="SearchSubmit" value="Search"/>
                </td>
            </tr>

        </table>
 
    </form>
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
        <a href="/?action=buy&id=<%= String.format("%s-%s-%s", car.getModel().getBrand().getID().getId(), car.getModel().getID().getId(), car.getID().getId())%>" class="buy" title="Buy this car.">Buy</a>
        <a href="/?action=ask&id=<%= String.format("%s-%s-%s", car.getModel().getBrand().getID().getId(), car.getModel().getID().getId(), car.getID().getId())%>" class="ask" title="Ask the dealer about this car.">Ask</a>
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

<%@include file="footer.jsp" %>
