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

    function getRolString(num) {
        if (num === -1) {
            return "Dibujar";
        } else if (num === -2) {
            return "Adivinar";
        } else {
            return "N/A";
        }
    }
    ;

    putGame = function () {
        var gameid = $("#topic").val();
        sessionStorage.setItem("currentgame", gameid);
        var word = "perro";
        return $.ajax({
            url: "/pictureci/normalMode/" + gameid,
            type: "PUT",
            data: word,
            contentType: "application/json"
        });
    };

    connectPlayer = function () {
        sessionStorage.setItem("rol", $("#rol").val());
        var rol = $("#rol").val();
        var user = sessionStorage.getItem("currentuser");
        var gameid = sessionStorage.getItem("currentgame");
        if (rol === "Adivinar") {
            return $.ajax({
                url: "/pictureci/normalMode/" + gameid + "/adivinan-" + user,
                type: "POST",
                data: user,
                contentType: "application/json",
                success: function () {
                    app.waiting();
                },
                error: function (request) {
                    alert(request.responseText);
                }
            });
        } else {
            return $.ajax({
                url: "/pictureci/normalMode/" + gameid + "/dibujan-" + user,
                type: "POST",
                data: user,
                contentType: "application/json",
                success: function () {
                    app.waiting();
                },
                error: function (request) {
                    alert(request.responseText);
                }
            });
        }
    };

    subscribeToWinner = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        var gameid = sessionStorage.getItem("currentgame");

        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected to game ' + gameid + ': ' + frame);
            stompClient.subscribe('/topic/winner.' + gameid, function (eventbody) {
                alert("Winner: " + eventbody.body);
            });
        });
    };

    subscribeToRoom = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        var gameid = sessionStorage.getItem("currentgame");

        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected to room ' + gameid + ': ' + frame);
            stompClient.subscribe('/topic/ready.' + gameid, function (data) {
                alert("wtf");
                app.makeGame(data);
            });
        });
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
                        sessionStorage.currentplayerName = user;
                        //createPlayer(sessionStorage.currentplayerName);
                        app.backToGameMode();

                    } else {
                        alert("CONTRASEÑA INVALIDA");
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
        connectToNormalGame: function () {
            putGame().then(function () {
                connectPlayer();
            }).then(function () {
                subscribeToRoom();
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
            }).then(function () {
                app.random();
            });
        },
        makeGame: function (type) {
            if (getRolString(type) === "Adivinar") {
                location.href = "rapidaAdivinador.html";
            } else {
                location.href = "rapidaDibujante.html";
            }
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
            var fail = function (data) {
                alert(data.responseText);
            };
            if (sessionStorage.getItem('rol') === "Adivinar") {

//                 $.ajax({
//                 url: "/pictureci/normalMode/" + sessionStorage.getItem("currentgame") + "/adivinan",
//                 type: "POST",
//                 success: () => {
//                 var data= {"name":sessionStorage.currentplayerName,"rol":"Adivina","room":sessionStorage.getItem("currentgame"),"score":0};
//                 addplayerToGame(data,sessionStorage.getItem("currentgame"),"adivinan");
//                 },
//                 error: fail
//                 });
//                var data = {"name": sessionStorage.currentplayerName, "rol": "Adivina", "room": sessionStorage.getItem("currentgame"), "score": 0};
//                addplayerToGame(data, sessionStorage.getItem("currentgame"), "adivinan");
            } else {
                /**
                 $.ajax({
                 url: "/pictureci/normalMode/" + sessionStorage.getItem("currentgame") + "/dibujan",
                 type: "POST",
                 success: () => {
                 var data= {"name":sessionStorage.currentplayerName,"rol":"Dibuja","room":sessionStorage.getItem("currentgame"),"score":0};
                 addplayerToGame(data,sessionStorage.getItem("currentgame"),"dibujan");
                 },
                 error: fail
                 });*/
                var data = {"name": sessionStorage.currentplayerName, "rol": "Dibuja", "room": sessionStorage.getItem("currentgame"), "score": 0};
                addplayerToGame(data, sessionStorage.getItem("currentgame"), "dibujan");
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
        },
        waiting: function () {
            location.href = "waiting.html";
        }
    };
})();