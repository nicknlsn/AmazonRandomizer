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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="main.js"></script>

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
                            <a href="accountinfo.jsp#address-form">Change my Shipping Address</a>
                            <a href="orderhistory.jsp">View my Order History</a>
                        </div>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </header>
        <main class="signupmain">
            <h1>Choose where your package is to be sent.</h1>
            <p id="errorHandling"></p>
            <!--<form id="address-input" onsubmit="return validate_address()">-->
            <form action="" id="address-form" >
                <!-- Cannot have budget here. They will be inside of the different PayPal buttons so we cannot set this dynamically here-->
<!--                Budget:
                <select name="budget" id="budget">
                    <option value="5">$5.00</option>
                    <option value="10">$10.00</option>
                    <option value="15">$15.00</option>
                    <option value="20">$20.00</option>
                    <option value="30">$30.00</option>
                    <option value="40">$40.00</option>
                    <option value="50">$50.00</option>
                </select> <br>-->
                Street 1 <input type="text" name="street1" id="street1">
                Street 2 <input type="text" name="street2" id="street2">
                City <input type="text" name="city" id="city">
                State 
                <select name="state" id="state">
                    <option value="Select">Select</option>
                    <option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>
                    <option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="DC">District Of Columbia</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>
                    <option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>
                    <option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>
                    <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>
                    <option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>
                    <option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>
                    <option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>
                    <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>
                    <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                </select>
                Zipcode <input type="text" name="zip" id="zip">
                Country: We currently only ship within the United States. 
                <select name="country" id="country">
                    <option value="US">United States</option>
                </select><br />
                Phone Number <input type="text" name="phone" id="phone" placeholder="XXXXXXXXXX">
                <!--<button onclick="validate_address()" type="submit">Continue</button>-->
                <input type="submit" value="Continue">
            </form>
            <!--We need a button to save this to our database and send them to paypal.-->
            <!--<p>Check out with PayPal and your mystery package will be sent soon!</p>-->
        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>
</html>
