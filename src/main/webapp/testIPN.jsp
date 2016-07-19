<%-- 
    Document   : testIPN
    Created on : Jul 18, 2016, 4:58:40 PM
    Author     : nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test IPN</title>
    </head>
    <body>
        <h1>Test IPN</h1>
        <form target="_new" method="post" action="http://localhost:8080/AmazonRandomizer/IPNListener">
            <input type="hidden" name="SomePayPalVar" value="SomeValue1"/>
            <input type="hidden" name="SomeOtherPPVar" value="SomeValue2"/>

            <!-- code for other variables to be tested ... -->

            <input type="submit"/>
        </form>
    </body>
</html>
