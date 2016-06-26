/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate() {
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var userName = $("#userName").val();
    var pwd = $("#pwd").val();
    var confPwd = $("#confPwd").val();

    alert("hello");

    if (firstName !== "" && lastName !== "" &&
            userName !== "" && pwd !== "" && confPwd !== "") {
        if (pwd !== confPwd) {
            // TODO: Tell user passwords don't match

            console.log("false2");
            return false;
        } else {
            var json = {fn: firstName, ln: lastName, email: emailAddress, un: userName, pass: pwd};

            $.post("SignUp", json)
                    .done(function (data) {
                        alert("Data loaded: " + data);
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
