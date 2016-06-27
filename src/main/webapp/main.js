/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate_signin() {
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val()
    var userName = $("#userName").val();
    var pwd = $("#pwd").val();
    var confPwd = $("#confPwd").val();

    if (firstName !== "" && lastName !== "" &&
            userName !== "" && pwd !== "" && 
            confPwd !== "" && email == "") {
        if (pwd !== confPwd) {
            // TODO: Tell user passwords don't match

            console.log("false2");
            return false;
        } else {
            var json = {fn: firstName, ln: lastName, e: email, un: userName, p: pwd};

            $.post("SignUp", json)
                    .done(function (data) {
                        console.log("Data loaded: " + data);
                    })
                    .fail(function () {
                        console.log("post failed");
                    })
                    .always(function () {
                        console.log("Do this when finished");
//                        location.replace("SignIn")
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

    if (userName !== "" && pwd !== "") {

        var json = {un: userName, p: pwd};

        $.post("SignIn", json)
                .done(function (data) {
                    console.log()"Data loaded: " + data);
                })
                .fail(function () {
                    console.log("post failed");
                })
                .always(function () {
                    console.log("Do this when finished");
//                        location.replace("SignIn")
                });


        console.log("true");
    }
}
