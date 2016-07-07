<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>About Amazon Randomizer</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="main.js" ></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
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
            <h1>Amazon Randomizer</h1>
            <h2>What is it?</h2>
            <p>Amazon Randomizer is a fun way for you to add something spontaneous to your life! It's super easy to use. Just create an account with us, set your budget, and we'll send you something from Amazon that fits your price range. It's as easy as that.</p>
            <p>We use paypal to take care of the transactions so you can be confident that your credit card information is safe and secure.</p>
            <h2>Here's how to do it:</h2>
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
        <h2 class="spaceabove">Need more of an explanation?</h2>
        <img src="pictures/cartoon.png" alt="random amazon items cartoon" class="centerimg cartoon">
        </main>
        <footer>
            <p class="centertext">Website created by Bree Carrick, Nick Nelson, and Thom Allen</p>
        </footer>
    </body>
</html>
