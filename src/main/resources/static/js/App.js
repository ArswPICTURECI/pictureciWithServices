/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var app = (function () {
    var stompClient = null;
    var currentUser;

    var connect = function () {
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/TOPICOXX', function (data) {


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
    
    var callbackUS=function (){};

    return{
        getUserName: function (userName) {
            if (userName !== "") {
                //$.get("/users/" + userName, callback);
                $.get("/users/" + userName ,callbackUS);
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
                currentUser = $.get("/users/"+user,callbackUS).then(function (){
                    location.href = "userSesion.html";
                },function (){
                    alert("El usuario que intenta agregar no esta Registrado");
                });
            }else{
                alert("El usuario no puede estar vacio!!");
            }


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