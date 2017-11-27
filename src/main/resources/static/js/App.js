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
                var markup = "<tr><td>" + ur.name + "</td></tr>";
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
        addUser: function (userName, pwd) {
            console.log("user " + userName + "pwd " + pwd);
            if (userName !== "") {
                var data = {"name": userName, "password": pwd};
                console.log(data);
                sessionStorage.setItem("currentuser", userName);
            } else {
                alert("El nombre del usuario no puede estar vacio");
            }
            return $.ajax({
                url: "/users",
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json"
            }).then(function () {
                $.get("/users", callback);
                alert("El usuario: " + userName + " se ha creado satisfactoriamente");
            },
                    function () {
                        alert("Error al registrar el nuevo Usuario");
                    }
            );
        },
        login: function (user, password) {
            if (user !== "") {
                $.get("/users/" + user, function (data) {
                    console.log(user + "y" + password);
                    if (password === password) {
                        sessionStorage.setItem("currentuser", user);
                        location.href = "GameMode.html";
                    } else {
                        alert("CONTRASEÑA INVALIDA")
                    }
                }).fail(function () {
                    alert("El usuario " + user + " no está registrado");
                });
            } else {
                alert("El usuario no puede estar vacio!!");
            }
        },

        subscribe: function () {
            sessionStorage.setItem('rol', $("#rol").val());
            var socket = new SockJS('/stompendpoint');
            var gameid = sessionStorage.getItem("currentgame");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected to game ' + gameid + ': ' + frame);
                stompClient.subscribe('/topic/winner.' + gameid, function (eventbody) {
                    alert("Winner: " + eventbody.body);
                });
            });

        },

        connect: function () {
            var gameid = $("#topic").val();
            sessionStorage.setItem("currentgame", gameid);
            var word = "perro";
            $.ajax({
                url: "/pictureci/" + gameid,
                type: "PUT",
                data: word,
                contentType: "application/json"
            }).then(() => {
                app.rapida();
            });
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected from game: " + sessionStorage.getItem("currentgame"));
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

        rapida: function () {
            sessionStorage.setItem('rol', $("#rol").val());
            var fail = (data) => {
                alert(data.responseText);
            };
            if (sessionStorage.getItem('rol') === "Adivinar") {
                $.ajax({
                    url: "/pictureci/" + sessionStorage.getItem("currentgame") + "/adivinan",
                    type: "PUT",
                    success: () => {
                        location.href = "rapidaAdivinador.html";
                    },
                    error: fail
                });
            } else {
                $.ajax({
                    url: "/pictureci/" + sessionStorage.getItem("currentgame") + "/dibujan",
                    type: "PUT",
                    success: () => {
                        location.href = "rapidaDibujante.html";
                    },
                    error: fail
                });
            }
        },

        registro: function () {
            location.href = "registerUser.html";
        },
        principal: function () {
            location.href = "index.html";
        },
        inicioSesion: function () {
            location.href = "sesion.html";
        },
        normalGame: function (){
            location.href = "partidaNormal.html";
        },
        
        randomGame:function (){
            location.href = "partidaRandom.html";
        },
        
        backToGameMode: function (){
            location.href = "GameMode.html";
        }
        
    };
})();