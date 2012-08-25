<?xml version="1.0"?>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="java.util.List"%>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<cars>
    <%
            List<Car> cars = (List<Car>) request.getAttribute("cars");
            for (Car car : cars) {
    %>
    <car id="<%= String.format("%s-%s-%s", car.getID().getParent().getParent().getId(), car.getID().getParent().getId(), car.getID().getId())%>">
        <dealer><%= car.getDealer().getId()%></dealer>
        <brand><%= car.getModel().getBrand().getName()%></brand>
        <model><%= car.getModel().getName()%></model>
        <engine><%= car.getEngine().toString()%></engine>
        <fuel><%= car.getFuel().toString()%></fuel>
        <power><%= car.getPower()%></power>
        <consumption><%= car.getConsumption()%></consumption>
        <price><%= car.getPrice()%></price>
        <description><%= car.getDescription()%></description>
    </car>
    <%
            }
    %>
</cars>