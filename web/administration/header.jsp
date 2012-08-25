<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="cz.cvut.fit.mi_mdw.util.Message"%>
<%@page import="java.util.List"%>
</head>
<body>
    <div id="main">
        <div id="header">
            <h1><a href="/"><span>Car Bazaar</span></a></h1>

            <ul>
                <li class="first"><a href="/" title="Homepage">Home</a></li>
                <li><a href="/admin" title="Cars">Cars</a></li>
                <li><a href="/admin?action=dealers" title="Dealers">Dealers</a></li>
                <li><a href="/admin?action=brands" title="Brands and models">Brands and models</a></li>
                <li><a href="/admin?action=users" title="Users">Users</a></li>
                <li><a href="/admin?action=orders" title="Orders">Orders</a></li>
                <li><a href="/admin?action=autosalonOrders" title="Autosalon orders">Autosalon orders</a></li>
                <li><a href="/admin?action=testData" title="Fill up with test data">Test</a></li>
                <li class="last"><a href="<%= UserServiceFactory.getUserService().createLogoutURL("/")%>" title="Logout">Logout</a></li>
            </ul>
        </div>


        <div id="AdminMessages">
            <%
                        List<Message> messages = (List<Message>) request.getAttribute("Messages");
                        for (Message m : messages) {
                            out.print(String.format("<p>%s</p>", m.getMessage()));
                        }
                        messages.clear();
            %>
        </div>