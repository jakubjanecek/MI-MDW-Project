<%@page import="cz.cvut.fit.mi_mdw.util.Message"%>
<%@page import="java.util.List"%>
</head>
<body>
    <div id="main">
        <div id="header">
            <h1><a href="/"><span>Car Bazaar</span></a></h1>

            <ul>
                <li class="first"><a href="/">Home</a></li>
                <li><a href="/?action=search">Search</a></li>
                <li class="last"><a href="/admin">Administration</a></li>
            </ul>
        </div>

        <div id="image">
            <img src="css/images/illustration.jpg" alt="Car Bazaar" />
        </div>
        <div id="FrontendMessages">
            <%
                    List<Message> messages = (List<Message>) request.getAttribute("Messages");
                    for (Message m : messages) {
                        out.print(String.format("<p>%s</p>", m.getMessage()));
                    }
                    messages.clear();
            %>
        </div>






