<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Autosalon Orders</title>

<%@include file="header.jsp" %>

<h2>Orders</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Date created</th>
            <th>Status</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        <%
                    NodeList listOfOrders = (NodeList) request.getAttribute("listOfOrders");

                    try {
                        for (int i = 0; i < listOfOrders.getLength(); i++) {

                            Node orderNode = listOfOrders.item(i);
                            if (orderNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element orderElement = (Element) orderNode;

                                // Date created
                                NodeList dateList = orderElement.getElementsByTagName("dateCreated");
                                Element dateElement = (Element) dateList.item(0);
                                NodeList textDateLibs = dateElement.getChildNodes();

                                // ID
                                NodeList idList = orderElement.getElementsByTagName("id");
                                Element idElement = (Element) idList.item(0);
                                NodeList textIdLibs = idElement.getChildNodes();

                                // status
                                NodeList statusList = orderElement.getElementsByTagName("status");
                                Element statusElement = (Element) statusList.item(0);
                                NodeList textStatusLibs = statusElement.getChildNodes();
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= ((Node) textIdLibs.item(0)).getNodeValue().trim()%></td>
            <td><%= ((Node) textDateLibs.item(0)).getNodeValue().trim()%></td>
            <td><%= ((Node) textStatusLibs.item(0)).getNodeValue().trim()%></td>
            <td align="center"><a href="#" title="Delete" class="DeleteAutosalonOrder" id="<%= ((Node) textIdLibs.item(0)).getNodeValue().trim()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-trash"></span></div></a></td>
        </tr>
        <%

                            }
                        }
                    } catch (NullPointerException e) {
                    }
        %>
    </tbody>
</table>

<div id="ConfirmationDialog" class="Dialog">
</div>

<%@include file="footer.jsp" %>
