<%@page import="cz.cvut.fit.mi_mdw.entities.Brand"%>
<%@page import="cz.cvut.fit.mi_mdw.entities.Model"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="htmlHead.jsp" %>

<title>Car Bazaar Administration - Brands and models</title>

<%@include file="header.jsp" %>

<ul class="clearfix submenu">
    <li><a href="#" title="Add brand" id="AddBrandButton">Add brand</a></li>
    <li><a href="#" title="Add model" id="AddModelButton">Add model</a></li>
</ul>

<h2>Brands</h2>
<table cellspacing="0" cellpadding="0" class="box admin">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Models</th>
            <th>Description</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        <%
                    List<Brand> brands = (List<Brand>) request.getAttribute("brands");
                    int i = 0;
                    for (Brand brand : brands) {
        %>
        <tr<% if (i % 2 != 0) {%> class="even" <% }%>>
            <td><%= brand.getID()%></td>
            <td><%= brand.getName()%></td>
            <td>
                <ul>
                    <%
                                        for (Model model : brand.getModels()) {
                                            out.print(String.format("<li>%s</li>", model.getName()));
                                        }
                    %>
                </ul>
            </td>
            <td><%= brand.getDescription()%></td>
            <td align="center"><a href="#" title="Edit" class="EditBrandButton" id="<%= brand.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-wrench"></span></div></a></td>
            <td align="center"><a href="#" title="Delete" class="DeleteBrandButton" id="<%= brand.getID().getId()%>"><div class="ui-state-default ui-corner-all Icon"><span class="ui-icon ui-icon-trash"></span></div></a></td>
        </tr>
        <%
                        i++;
                    }
        %>
    </tbody>
</table>

<div id="AddBrandFormDiv" class="Dialog">
    <form method="post" action="/admin?action=addBrand" id="AddBrandForm" name="AddBrandForm">
        <label for="AddBrandName">Name</label>
        <input type="text" id="AddBrandName" name="AddBrandName"/>
        <label for="AddBrandDescription">Description</label>
        <input type="text" id="AddBrandDescription" name="AddBrandDescription"/>
    </form>
</div>
<%
            for (Brand brand : brands) {
%>
<div class="EditBrandFormDiv-<%= brand.getID().getId()%> Dialog">
    <form method="post" action="/admin?action=editBrand" class="EditBrandForm-<%= brand.getID().getId()%>" name="EditBrandForm">
        <input type="hidden" id="EditBrandID" name="EditBrandID" value="<%= brand.getID().getId()%>"/>
        <label for="EditBrandName">Name</label>
        <input type="text" id="AddBrandName" name="EditBrandName" value="<%= brand.getName()%>"/>
        <label for="EditBrandDescription">Description</label>
        <input type="text" id="EditBrandDescription" name="EditBrandDescription" value="<%= brand.getDescription()%>"/>
    </form>
</div>
<%        }
%>

<div id="AddModelFormDiv" class="Dialog">
    <form method="post" action="/admin?action=addModel" id="AddModelForm" name="AddModelForm">
        <label for="AddModelBrand">Brand</label>
        <select name="AddModelBrand" id="AddModelBrand">
            <%
                        for (Brand brand : brands) {
                            out.print(String.format("<option value=\"%s\">%s</option>", brand.getID().getId(), brand.getName()));
                        }
            %>
        </select><br/>
        <label for="AddModelName">Name</label>
        <input type="text" id="AddModelName" name="AddModelName"/>
        <label for="AddModelDescription">Description</label>
        <input type="text" id="AddModelDescription" name="AddModelDescription"/>
    </form>
</div>

<div id="ConfirmationDialog" class="Dialog">
</div>

<%@include file="footer.jsp" %>
