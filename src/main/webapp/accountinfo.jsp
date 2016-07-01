<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>

    <head>
        <title>Account Info Amazon Randomizer</title>
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
             <h1>Account Info</h1>
             <a href="orderhistory.jsp" class="link">View my order history.</a>
            <form id="account">First Name
                <input type="text" name="firstName" id="firstName" />
                <br />Last Name
                <input type="text" name="lastName" id="lastName" />
                <br />Email Address
                <input type="text" name="email" id="email" />
                <br />User Name
                <input type="text" name="userName" id="userName" />
                <br />Current Password
                <input type="password" name="pwd" id="pwd" />
                <br />New Password
                <input type="password" name="newpwd" id="newpwd" />
                <br />Confirm New Password
                <input type="password" name="confPwd" id="confPwd" />
                <br />
                <button>Save Changes</button>
            </form>
             <h3>Shipping Address</h3>
            <form id="address">Street 1
                <input type="text" name="street1">Street 2
                <input type="text" name="street2">City
                <input type="text" name="city">State
                <input type="text" name="state">Zipcode
                <input type="text" name="zip">Country
                <input type="text" name="country">Phone Number
                <input type="text" name="phone">
                <button>Save Changes</button>
            </form>
        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>

</html>