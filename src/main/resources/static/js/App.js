/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var app = (function () {

    var stompClient = null;
    var currentUser;
    var gameid;

    var subscribe = function () {
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/winner.' + gameid, function (data) {


            });
        });
    };

    var disconnect = function () {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    };

    var callback = function (lista) {

        $("#tablaUsers tbody").empty();
        lista.map(function (ur) {
            $(document).ready(function () {
                var markup = "<tr><td>" + ur.name + "</td><td>" + ur.rol + "</td><td>" + ur.sala + "</td></tr>";
                $("#tablaUsers tbody").append(markup);
            });
        }
        );
    };

    var setuser = function (user) {
        currentUser = user;
    };

    return{
        getUserName: function (userName) {
            if (userName !== "") {
                //$.get("/users/" + userName, callback);
                $.get("/users/" + userName);
            } else {
                alert("EL USUARIO NO PUEDE ESTAR VACIO");
            }
            ;
        },
        addUser: function (userName) {
            console.log("UserName: " + userName);
            if (userName !== "") {
                var data = {"name": userName, "rol": "", "sala": 0};
                currentUser = data;
            } else {
                alert("El nombre del usuario no puede estar vacio");
            }

            return $.ajax({
                url: "/users/",
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json"
            }).then(function () {
                $.get("/users/", callback);
                alert("El usuario: " + userName + " se ha creado satisfactoriamente");
            },
                    function () {
                        alert("Error al registrar el nuevo Usuario");
                    }
            );
        },
        login: function (user) {
            if (user !== "") {
                $.get("/users/" + user, function (data) {
                    currentUser = data;
                    location.href = "partida.html";
                }).fail(function () {
                    alert("El usuario " + user + " no está registrado");
                });
            } else {
                alert("El usuario no puede estar vacio!!");
            }
        },

        connect: function (game) {
            var game_ = {used_words: "", word: "dog", winner: ""};
            $.ajax({
                url: "/pictureci/creategame/" + game,
                type: "POST",
                data: JSON.stringify(game_),
                contentType: "application/json"
            }).then(() => {
                app.rapida();

            }).then(() => {
                subscribe();
            });
        },

        attempt: function () {
            $.get("/users/currentuser", (data) => {
                currentUser = data;
            }).then(() => {
                var attemt = {username: currentUser, phrase: $("#guess_input").val()};
                $.ajax({
                    url: "/pictureci/" + gameid + "/guess",
                    type: "POST",
                    data: JSON.stringify(attempt),
                    contentType: "application/json"
                });
            });
        },

        queryUsers: function () {
            $.get("/users/", callback);
        },
        partida: function () {
            location.href = "partida.html";
        },
        rapida: function () {
            location.href = "rapida.html";
        },
        registro: function () {
            location.href = "registro.html";
        },
        principal: function () {
            location.href = "index.html";
        },
        inicioSesion: function () {
            location.href = "sesion.html";
        }
    };
})();