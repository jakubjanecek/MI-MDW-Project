<%
        boolean result = (Boolean) request.getAttribute("result");
        if (result) {
            out.print("true");
        }
        else {
            out.print("false");
        }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>