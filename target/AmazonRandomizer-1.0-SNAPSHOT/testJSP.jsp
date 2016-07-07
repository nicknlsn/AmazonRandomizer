<%-- 
    Document   : testJSP
    Created on : Jul 2, 2016, 12:45:09 PM
    Author     : Thom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Test Sessions User Name: <%= session.getAttribute("userName")%></h1>
    </body>
</html>
