/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var app = (function () {

    var stompClient = null;

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
            if (userName !== "") {
                var data = {"name": userName, "rol": "", "sala": 0};
                sessionStorage.setItem("currentuser", userName);
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
                    sessionStorage.setItem("currentuser", user);
                    location.href = "partida.html";
                }).fail(function () {
                    alert("El usuario " + user + " no está registrado");
                });
            } else {
                alert("El usuario no puede estar vacio!!");
            }
        },

        connect: function () {
            var game = $("#topic").val();
            sessionStorage.setItem("currentgame", game);
            var game_ = {"used_words": [], "word": "dog", "winner": ""};
            $.ajax({
                url: "/pictureci/" + game,
                type: "POST",
                data: JSON.stringify(game_),
                contentType: "application/json"
            }).then(function(){
                var socket = new SockJS('/stompendpoint');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    alert('Connected jiji: ' + frame);
                    var gameid = sessionStorage.getItem("currentgame");
                    stompClient.subscribe("/topic/winner." + gameid, function (data) {
                        alert("Winner: " + data.body);
                    });
                });
            },function (){
                app.rapida();
                
            });
        },

        attempt: function () {
            var cgame = sessionStorage.getItem("currentgame");
            var cuser = sessionStorage.getItem("currentuser");
            var att = {"username": cuser, "phrase": $("#guess_input").val()};
            return $.ajax({
                url: "/pictureci/" + cgame + "/guess",
                type: "POST",
                data: JSON.stringify(att),
                contentType: "application/json"
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