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
                            location.replace("testJSP.jsp")
                        }
                    
                    })
                    .fail(function (data) {
                        console.log("post failed: " + data);
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
                })
                .fail(function (data) {
                    console.log("post failed: " + data);
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
