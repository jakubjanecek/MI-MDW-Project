<%@page import="cz.cvut.fit.mi_mdw.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Users</title>

<%@include file="header.jsp" %>

<ul class="clearfix submenu">
    <li><a href="#" title="Add user" id="AddUserButton">Add user</a></li>
</ul>

<h2>Users</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Name</th>
            <th>Address</th>
            <th>E-mail</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        <%
                List<User> users = (List<User>) request.getAttribute("users");
                int i = 0;
                for (User user : users) {
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= user.getID().getId()%></td>
            <td><%= user.getUsername()%></td>
            <td><%= user.getName()%></td>
            <td><%= user.getAddress()%></td>
            <td><%= user.getEmail()%></td>
            <td align="center"><a href="#" title="Edit" class="EditUserButton" id="<%= user.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-wrench"></span></div></a></td>
            <td align="center"><a href="#" title="Delete" class="DeleteUserButton" id="<%= user.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-trash"></span></div></a></td>
        </tr>
        <%
        i++;
                }
        %>
    </tbody>
</table>

<div id="AddUserFormDiv" class="Dialog">
    <form method="post" action="/admin?action=addUser" id="AddUserForm" name="AddUserForm">
        <label for="AddUserName">Name</label>
        <input type="text" id="AddUserName" name="AddUserName"/>
        <label for="AddUserUsername">Username</label>
        <input type="text" id="AddUserUsername" name="AddUserUsername"/>
        <label for="AddUserPassword">Password</label>
        <input type="text" id="AddUserPassword" name="AddUserPassword"/>
        <label for="AddUserAddress">Address</label>
        <input type="text" id="AddUserAddress" name="AddUserAddress"/>
        <label for="AddUserEmail">E-mail</label>
        <input type="text" id="AddUserEmail" name="AddUserEmail"/>
    </form>
</div>

<%
        for (User user : users) {
%>
<div class="EditUserFormDiv-<%= user.getID().getId()%> Dialog">
    <form method="post" action="/admin?action=editUser" class="EditUserForm-<%= user.getID().getId()%>" id="EditUserForm" name="EditUserForm">
        <input type="hidden" id="EditUserID" name="EditUserID" value="<%= user.getID().getId()%>"/>
        <label for="EditUserName">Name</label>
        <input type="text" id="EditUserName" name="EditUserName" value="<%= user.getName()%>"/>
        <label for="EditUserUsername">Username</label>
        <input type="text" id="EditUserUsername" name="EditUserUsername" value="<%= user.getUsername()%>"/>
        <label for="EditUserPassword">Password</label>
        <input type="text" id="EditUserPassword" name="EditUserPassword" value="<%= user.getPassword()%>"/>
        <label for="EditUserAddress">Address</label>
        <input type="text" id="EditUserAddress" name="EditUserAddress" value="<%= user.getAddress()%>"/>
        <label for="EditUserEmail">E-mail</label>
        <input type="text" id="EditUserEmail" name="EditUserEmail" value="<%= user.getEmail()%>"/>
    </form>
</div>
<%
        }
%>

<div id="ConfirmationDialog" class="Dialog">
</div>

<%@include file="footer.jsp" %>
