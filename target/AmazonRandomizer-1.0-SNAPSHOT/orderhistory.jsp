<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>

    <head>
        <title>Order History Amazon Randomizerr</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--Stylesheets and fonts-->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro">
        <link href="https://fonts.googleapis.com/css?family=Slackey" rel="stylesheet">
        <link rel="stylesheet" href="randomizer.css">

    </head>

    <body>
        <header>
            <nav>
                <a href="index.jsp">
                    <img src="pictures/navlogo.png" alt="Randomizer Logo" class="navimg">
                </a>
                <a href="index.jsp">
                    <img src="pictures/navtextlogo.png" alt="Amazon Randomizer" class="navimg">
                </a>
                <ul>
                    <%
                        if (session.getAttribute("userName") == null || session.getAttribute("userName") == "") {
                    %>
                    <!--Navigation for user who is not logged in-->
                    <li><a href="index.jsp">Home</a>
                    </li>
                    <li><a href="login.jsp">Login</a>
                    </li>
                    <li><a href="about.jsp">About</a>
                    </li>
                    <li><a href="signup.jsp">Get Started</a>
                    </li>
                    <% } else { %>
                    <!--Navigation for user who is logged in-->
                    <li><a href="index.jsp">Home</a>
                    </li>
                    <li><a href="Logout">Logout</a>
                    </li>
                    <li><a href="about.jsp">About</a>
                    </li>
                    <li><a href="set_address.jsp">Get Started</a>
                    </li>
                    <li class="dropdown"><a href="accountinfo.jsp" class="dropbtn">Hello, <%= session.getAttribute("userName")%></a>
                        <div class="dropdown-content">
                            <a href="accountinfo.jsp#account">Change Account Information</a>
                            <a href="accountinfo.jsp#address">Change my Shipping Address</a>
                            <a href="orderhistory.jsp">View my Order History</a>
                        </div>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </header>
        <main class="signupmain">
             <h1>Order History</h1>
             
        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>

</html>