<%@page import="cz.cvut.fit.mi_mdw.entities.Dealer"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Car"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Dealers</title>

<%@include file="header.jsp" %>

<ul class="clearfix submenu">
    <li><a href="#" title="Add dealer" id="AddDealerButton">Add dealer</a></li>
</ul>

<h2>Dealers</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>E-mail</th>
            <th>Phone</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        <%
                    List<Dealer> dealers = (List<Dealer>) request.getAttribute("dealers");
                    int i = 0;
                    for (Dealer dealer : dealers) {          
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= dealer.getID().getId()%></td>
            <td><%= dealer.getName()%></td>
            <td><%= dealer.getAddress()%></td>
            <td><%= dealer.getEmail()%></td>
            <td><%= dealer.getPhone()%></td>
            <td align="center"><a href="#" title="Edit" class="EditDealerButton" id="<%= dealer.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-wrench"></span></div></a></td>
            <td align="center"><a href="#" title="Delete" class="DeleteDealerButton" id="<%= dealer.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-trash"></span></div></a></td>
        </tr>
        <%
                        i++;
                    }
        %>
    </tbody>
</table>

<div id="AddDealerFormDiv" class="Dialog">
    <form method="post" action="/admin?action=addDealer" id="AddDealerForm" name="AddDealerForm">
        <label for="AddDealerName">Name</label>
        <input type="text" id="AddDealerName" name="AddDealerName"/>
        <label for="AddDealerAddress">Address</label>
        <input type="text" id="AddDealerAddress" name="AddDealerAddress"/>
        <label for="AddDealerEmail">E-mail</label>
        <input type="text" id="AddDealerEmail" name="AddDealerEmail"/>
        <label for="AddDealerPhone">Phone</label>
        <input type="text" id="AddDealerPhone" name="AddDealerPhone"/>
    </form>
</div>

<%
            for (Dealer dealer : dealers) {
%>
<div class="EditDealerFormDiv-<%= dealer.getID().getId()%> Dialog">
    <form method="post" action="/admin?action=editDealer" class="EditDealerForm-<%= dealer.getID().getId()%>" name="EditDealerForm">
        <input type="hidden" id="EditDealerID" name="EditDealerID" value="<%= dealer.getID().getId()%>"/>
        <label for="EditDealerName">Name</label>
        <input type="text" id="EditDealerName" name="EditDealerName" value="<%= dealer.getName()%>"/>
        <label for="EditDealerAddress">Address</label>
        <input type="text" id="EditDealerAddress" name="EditDealerAddress" value="<%= dealer.getAddress()%>"/>
        <label for="EditDealerEmail">E-mail</label>
        <input type="text" id="EditDealerEmail" name="EditDealerEmail" value="<%= dealer.getEmail()%>"/>
        <label for="EditDealerPhone">Phone</label>
        <input type="text" id="EditDealerPhone" name="EditDealerPhone" value="<%= dealer.getPhone()%>"/>
    </form>
</div>
<%        }
%>

<div id="ConfirmationDialog" class="Dialog">
</div>

<%@include file="footer.jsp" %>
