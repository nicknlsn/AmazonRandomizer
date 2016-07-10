/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//$(document).ready(funciton() {
////    $("#fnError").hide();
////    $("#lnError").hide();
////    $("#eError").hide();
////    $("#unError").hide();
////    $("#pwdError").hide();
////    $("#confError").hide();
//});

function validate_signin() {
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val();
    var userName = $("#userName").val();
    var pwd = $("#pwd").val();
    var confPwd = $("#confPwd").val();
    if (firstName && lastName && userName && pwd && confPwd && email) {
        if (pwd !== confPwd) {
// TODO: Tell user passwords don't match

            console.log("false2");
            return false;
        } else {
            var json = {fn: firstName, ln: lastName, e: email, un: userName, p: pwd};
            $.post("SignUp", json)
                    .done(function (data) {
                        console.log("Data loaded: " + data);
                        if (data == "email_error") {
                            // TODO: Display email error 
                            $("#email").css("background-color", "pink");
                        } else {
                            $("#email").css("background-color", "white");
                            console.log("Should redirect to testJSP.jsp")
//                            window.location = "testJSP.jsp"
                            location.replace("index.jsp")
                        }

                    })
                    .fail(function (data) {
                        console.log("post failed: " + data);
                        alert("Sorry, Server Error.");
                        location.replace("index.jsp");
                    })
                    .always(function (data) {
                        console.log("Do this when finished: " + data);
                        //window.location = "index.html";
                        // location.replace("SignIn")
                    });
            console.log("true");
            return true;
        }
    } else {
// TODO: Tell user they have to input info

        console.log("false1");
        return false;
    }
}

function validate_login() {
    var userName = $("#userName").val();
    var pwd = $("#pwd").val();
    if (userName && pwd) {

        var json = {un: userName, p: pwd};
        $.post("Login", json)
                .done(function (data) {
                    console.log("Data loaded: " + data);
                    if (data == "login_error") {
                        // TODO: Make the user aware that this email and password combo are invalid 
                        $("#email").css("background-color", "pink");
                        $("#pwd").css("background-color", "pink");
                        alert("Email or Password is invalid");
                    } else {
                        $("#email").css("background-color", "white");
                        $("#pwd").css("background-color", "white");
                        console.log("Should redirect to testJSP.jsp")
//                            window.location = "testJSP.jsp"
                        location.replace("index.jsp");
                    }
                })
                .fail(function (data) {
                    console.log("post failed: " + data);
                    alert("Sorry, Server Error.");
                    location.replace("index.jsp");
                })
                .always(function (data) {
                    console.log("Do this when finished: " + data);
//                        location.replace("SignIn")
                });
        console.log("true");
        return true;
    } else {
        return false;
    }
}

function getLength(number) {
    return number.toString().length;
}

$(document).ready(function () {

//function validate_address() {
    $("#address-form").submit(function (e) {
        e.preventDefault();
//        alert("hello");
//        return false;
//    return false;
        var street1 = $("#street1").val();
        var street2 = $("#street2").val();
        var city = $("#city").val();
        var state = $("#state").val();
        var zip = $("#zip").val();
        var country = $("#country").val();
        var phone = $("#phone").val();

        console.log(street1);
        console.log(street2);
        console.log(city);
        console.log(state);
        console.log(zip);
        console.log(country);
        console.log(phone);
        if (street1 && city && state && zip && country && phone) {
            if (getLength(phone) < 10 || getLength(phone) > 10) {
// Phone number not valid
                console.log("Phone number not valid")
                return false;
            } else if (state == "select") {
                console.log("State not selected")
                return false;
            } else {
// Good
                var json = {s1: street1, s2: street2, c: city,
                    s: state, z: zip, ctr: country, ph: phone};
                $.post("AddressHandler", json)
                        .done(function (data) {
                            console.log("Data loaded: " + data);
                            if (data == "street1") {
                                // TODO: Display email error 
                                $("#email").css("background-color", "pink");
                            } else if (data == "city_error") {

                            } else if (data == "state_error") {

                            } else if (data == "zip_error") {

                            } else if (data == "country_error") {

                            } else if (data == "phone_error") {

                            } else {  // No errors
                                //$("#email").css("background-color", "white");
                                console.log("Should redirect to testJSP.jsp")
//                            window.location = "testJSP.jsp"
                                location.replace("purchase.jsp")
                            }

                        })
                        .fail(function (data) {
                            console.log("post failed: " + data);
                            alert("Sorry, Server Error.");
//                        location.replace("index.html");
                        })
                        .always(function (data) {
                            console.log("Do this when finished: " + data);
                            //window.location = "index.html";
                            // location.replace("SignIn")
                        });
            }
        } else {
// Something is null
            alert("issues");
            return false;
        }
    });
    $("#address-form").ready(function () {
        $.post("GetAddress")
                .done(function (data) {
                    console.log("Data loaded: " + data);
                    
                    var json = JSON.parse(data);
                    $("#street1").val(json["s1"]);
                    $("#street2").val(json["s2"]);
                    $("#city").val(json["c"]);
                    $("#state").val(json["s"]);
                    $("#zip").val(json["z"]);
                    $("#country").val(json["ctr"]);
                    $("#phone").val(json["ph"]);

                })
                .fail(function (data) {
                    console.log("post failed: ");
                    var json = data;
                    console.log(json);
                    alert("Sorry, Server Error.");
//                        location.replace("index.html");
                })
                .always(function (data) {
                    
                });
    })
    
    $("#street1, #street2, #city, #state, #zip, #country, #phone").focus(function(){
        $(this).select();
    })
    
});
