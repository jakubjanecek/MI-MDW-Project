<%@page import="cz.cvut.fit.mi_mdw.entities.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Orders</title>

<%@include file="header.jsp" %>

<h2>Orders</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>User</th>
            <th>Car</th>
            <th>Dealer</th>
        </tr>
    </thead>
    <tbody>
        <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                int i = 0;
                for (Order order : orders) {
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= order.getID().getId()%></td>
            <td><%= order.getDatetime().toString()%></td>
            <td><%= order.getUser()%></td>
            <td><%= order.getCar()%></td>
            <td><%= order.getDealer().getName()%></td>
        </tr>
        <%
        i++;
                }
        %>
    </tbody>
</table>

<%@include file="footer.jsp" %>
