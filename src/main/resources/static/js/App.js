/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = (function () {
    
    //sessionStorage.currentplayerName="";

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

    function getRolString(num) {
        if (num === -1) {
            return "Dibuja";
        } else if (num === -2) {
            return "Adivina";
        }else{
            return "N/A";
        }
    };
    
    function addplayerToGame(playerInfo,gameid,tipoJugador){
        //console.log(playerInfo + "dsa" + gameid + "dsa "+ tipoJugador);
        return $.ajax({
                url: "/pictureci/normalMode/" + gameid + "/"+tipoJugador+"/"+playerInfo,
                type: 'POST',
                data: JSON.stringify(playerInfo),
                contentType: "application/json"
            }).then(function () {
                $.get("/users", callback);
                alert("El Jugador: " + playerInfo.name + " se ha creado satisfactoriamente");
            },
                    function () {
                        alert("Error al registrar el nuevo Jugador");
                    }
            );
    };
    
    /**
    function getPlayer(player){
        if (userName !== "") {
            return $.get("/players/" + player);
        } else {
            return alert("NO EXISTE EL JUGADOR");
        };
    }*/

    callbackPlayers = function (lista) {
        $("#tabla tbody").empty();
        lista.map(function (ur) {
            $(document).ready(function () {
                var markup = "<tr><td>" + ur.name + "</td><td>" + ur.room + "</td><td>" + getRolString(ur.rol) + "</td><td>" + ur.score + "</td></tr>";
                $("#tabla tbody").append(markup);
            });
        }
        );

    };

    return{
        getUserName: function (userName) {
            if (userName !== "") {
                $.get("/users/" + userName);
            } else {
                alert("EL USUARIO NO PUEDE ESTAR VACIO");
            }
            ;
        },
        
        addUser: function (userName, pwd) {

            if (userName !== "") {
                var data = {"name": userName, "password": pwd};
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
            if (user !== "" && password !== "") {
                $.get("/users/" + user, function (data) {
                    if (password === password) {
                        sessionStorage.setItem("currentuser", user);
                        sessionStorage.currentplayerName=user;
                        location.href = "GameMode.html";
                        
                    } else {
                        alert("CONTRASEÑA INVALIDA")
                    }
                }).fail(function () {
                    alert("El usuario " + user + " no está registrado");
                });
            } else if (user !== "" && password === "") {
                alert("Debe Digitar una contraseña");
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

        connectToNormalGame: function () {
            var gameid = $("#topic").val();
            sessionStorage.setItem("currentgame", gameid);
            var word = "perro";
            $.ajax({
                url: "/pictureci/normalMode/" + gameid,
                type: "PUT",
                data: word,
                contentType: "application/json"
            }).then(() => {
                app.rapida();
            });
        },

        //TOCA MODIFICARLO. FALTA
        connectToRandomGame: function () {
            var gameid = $("#topic").val();
            sessionStorage.setItem("currentgame", gameid);
            var word = "perro";
            $.ajax({
                url: "/pictureci/" + gameid,
                type: "PUT",
                data: word,
                contentType: "application/json"
            }).then(() => {
                app.random();
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
                url: "/pictureci/normalMode/" + cgame + "/guess",
                type: "POST",
                data: JSON.stringify(att),
                contentType: "application/json"
            });
        },
        queryUsers: function () {
            $.get("/users/", callback);
        },
        queryPlayers: function () {
            $.get("/players/", callbackPlayers);
        },

        rapida: function () {
            sessionStorage.setItem('rol', $("#rol").val());
            var fail = (data) => {
                alert(data.responseText);
            };
            if (sessionStorage.getItem('rol') === "Adivinar") {
                $.ajax({
                    url: "/pictureci/normalMode/" + sessionStorage.getItem("currentgame") + "/adivinan",
                    type: "POST",
                    success: () => {
                        var data= {"name":sessionStorage.currentplayerName,"rol":"Adivina","room":sessionStorage.getItem("currentgame"),"score":0};
                        addplayerToGame(data,sessionStorage.getItem("currentgame"),"adivinan");
                        location.href = "rapidaAdivinador.html";
                    },
                    error: fail
                });
            } else {
                $.ajax({
                    url: "/pictureci/normalMode/" + sessionStorage.getItem("currentgame") + "/dibujan",
                    type: "POST",
                    success: () => {
                        alert("Nombre de jugador "+sessionStorage.currentplayerName+" rol: dibuja y sala: "+ sessionStorage.getItem("currentgame"));
                        var data= {"name":sessionStorage.currentplayerName,"rol":"Dibuja","room":sessionStorage.getItem("currentgame"),"score":0};
                        addplayerToGame(data,sessionStorage.getItem("currentgame"),"dibujan");
                        location.href = "rapidaDibujante.html";
                    },
                    error: fail
                });
            }
        },

        random: function () {

            //PENDIENTE

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
        normalGame: function () {
            location.href = "partidaNormal.html";
        },

        randomGame: function () {
            location.href = "partidaRandom.html";
        },

        backToGameMode: function () {
            location.href = "GameMode.html";
        }

    };
})();