<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login to Amazon Randomizer</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="main.js" ></script>
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
                    <li><a href="index.jsp">Home</a>
                    </li>
                    <li><a href="login.jsp">Login</a>
                    </li>
                    <li><a href="about.jsp">About</a>
                    </li>
                    <li><a href="signup.jsp">Get Started</a>
                    </li>
                    <li class="dropdown"><a href="accountinfo.jsp" class="dropbtn">Users Name</a>
                        <div class="dropdown-content">
                             <a href="accountinfo.jsp#account">Change Account Information</a>
                             <a href="accountinfo.jsp#address">Change my Shipping Address</a>
                             <a href="orderhistory.jsp">View my Order History</a>
                        </div>
                    </li>
                </ul>
            </nav>
    </header>
        <main class="signupmain">
            <h3 class="signup">Login to Amazon Randomizer</h3>
            <!--<form action="Login" method="POST" >-->
            <div class="form">
            User Name<input type="text" name="userName" id="userName"/><br />
            Password<input type="password" name="pwd" id="pwd"/><br />
            <button onclick="validate_login()">Login</button>
            </div>
            <!--</form>-->

        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>
</html>
