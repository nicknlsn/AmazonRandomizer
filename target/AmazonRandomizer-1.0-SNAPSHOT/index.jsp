<!DOCTYPE html>

<% if (session.getAttribute("userName") == null || session.getAttribute("userName").equals("")) {
        response.sendRedirect("index.html");
    } else {
%>
<html>

    <head>
        <title>Amazon Randomizer</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Stylesheets and fonts-->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro">
        <link href="https://fonts.googleapis.com/css?family=Slackey" rel="stylesheet">
        <link rel="stylesheet" href="randomizer.css">
        <link rel="import" href="analytics.html"> <!-- don't know how to make this work... -->
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
                    <li><a href="Logout">Logout</a>
                    </li>
                    <li><a href="about.jsp">About</a>
                    </li>
                    <li><a href="signup.jsp">Get Started</a>
                    </li>
                    <li class="dropdown"><a href="accountinfo.jsp" class="dropbtn">Hello, <%= session.getAttribute("userName")%></a>
                        <div class="dropdown-content">
                            <a href="accountinfo.jsp#account">Change Account Information</a>
                            <a href="accountinfo.jsp#address">Change my Shipping Address</a>
                            <a href="orderhistory.jsp">View my Order History</a>
                        </div>
                    </li>
                </ul>
            </nav>
        </header>
        <main>
            <!--First Horizontal Section-->
            <div class="block1">
                <img src="pictures/boxlogo.png" alt="Amazon Randomizer Logo" class="centerimg mainimg">
                <h1 class="colorbackground centertext">Amazon Randomizer</h1>
                <p class="colorbackground centertext">You set your budget and a mystery package is delivered to you a few days later.</p>
            </div>
            <!--Second Horizontal Section-->
            <div class="block2">
                <!--column one-->
                <div class="colblock centertext">
                    <h3>Step 1. Sign Up</h3>
                    <img src="pictures/checkmark.png" alt="sign up" class="centerimg">
                    <p>Click the Get Started tab at the top of the page and create an account with us.</p>
                </div>
                <!--column two-->
                <div class="colblock centertext">
                    <h3>Step 2. Set your Budget</h3>
                    <img src="pictures/dollarsign.png" alt="set your budget" class="centerimg">
                    <p>Choose how much you want to spend and fill in your shipping address.</p>
                </div>
                <!--column three-->
                <div class="colblock centertext">
                    <h3>Step 3. Paypal</h3>
                    <img src="pictures/paypal.png" alt="paypal" class="centerimg">
                    <p>Complete your transaction through paypal.</p>
                </div>
                <div class="laststep">
                    <h3 class="centertext">Step 4. Wait Patiently.</h3>
                    <img src="pictures/clock.png" alt="wait patiently" class="centerimg">
                    <p class="centertext">Within a few days you will recieve your package from Amazon.</p>
                </div>
            </div>
            <!--Third Horizontal Section-->
            <div class="block3">
                <h2 class="centertext wantin">Want in?</h2>
                <p class="centertext">Really, why wouldn't you?</p>
                <p class="centertext"><a href="signup.jsp" class="button">Get Started</a></p>
            </div>
            <footer>
                <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
            </footer>
        </main>
    </body>

</html>
<%}%>