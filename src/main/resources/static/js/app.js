/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var app = (function () {
    var stompClient = null;


    //FALTA CONECTAR 
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
    
    var callback=function (){};

    return{

        getUserName: function (userName) {
            if (userName !== "") {
                //$.get("/users/" + userName, callback);
                $.get("/users/" + userName);
            } else if (authname == "") {
                alert("EL USUARIO NO PUEDE ESTAR VACIO");
            }
            ;
        },

        //BOTA UN ERROR EN EL AJAX, AUN NO LO LOGRO IDENTIFICAR
        addUser: function (userName) {
            console.log("UserName: "+ userName);
            //var data = {"name": userName, "rol": "", "sala": 0};
            var data = {"name":userName,"rol":"","sala":0};
            return $.ajax({
                url: "/users/",
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json"
            }).then(function () {
                $.get("/users/" , callback);
            },
                    function () {
                        alert("Error al registrar el nuevo Usuario");
                    }
            );
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
        
        principal:function (){
            location.href = "index.html";
        }

        
        /**
         $(document).ready(
         function () {
         connect();
         console.info('connecting to websockets');
         }
         )*/

    };




})();