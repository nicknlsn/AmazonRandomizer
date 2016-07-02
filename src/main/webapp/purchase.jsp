<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Purchase Amazon Randomizer</title>
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
            <h1>Choose how much you want to spend and where to send your package.</h1>
            <form>
                Budget:
                <select name="budget">
                    <option value="5">$5.00</option>
                    <option value="10">$10.00</option>
                    <option value="15">$15.00</option>
                    <option value="20">$20.00</option>
                    <option value="30">$30.00</option>
                    <option value="40">$40.00</option>
                    <option value="50">$50.00</option>
                </select> <br>
                Street 1 <input type="text" name="street1">
                Street 2 <input type="text" name="street2">
                City <input type="text" name="city">
                State <input type="text" name="state">
                Zipcode <input type="text" name="zip">
                Country <input type="text" name="country">
                Phone Number <input type="text" name="phone">
            </form>
            <!--We need a button to save this to our database and send them to paypal.-->
            <p>Check out with paypal and your mystery package will be sent soon!</p>
        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>
</html>
