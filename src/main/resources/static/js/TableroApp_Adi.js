/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var stompClient = null;
var x = 0;
var y = 0;
var canvas;
var context;
var canvasLimites;
var flagPaint = false;
var actualPos;
var sala=null;
prepareCanvas = function () {
    canvas = document.getElementById("myCanvas");
    ctx = canvas.getContext("2d");
    canvasLimites = canvas.getBoundingClientRect();
    canvas.addEventListener('mousedown', cambiarEstado, false);
    canvas.addEventListener('mouseup', cambiarEstado, false);
    canvas.addEventListener('mousemove', pintarLinea, false);
    canvas.style.cursor = "hand";

};

cambiarEstado = function () {
    flagPaint = !flagPaint;
    actualPos = obtenerCoordenadas(event);
};


pintarLinea = function (event) {
    if (flagPaint) {
        var coordenadas = obtenerCoordenadas(event);
        ctx.beginPath();
        ctx.moveTo(actualPos.x, actualPos.y);
        ctx.lineTo(coordenadas.x, coordenadas.y);
        actualPos = {
            x: coordenadas.x,
            y: coordenadas.y
        };
        ctx.stroke();
    }
};
obtenerCoordenadas = function (event) {
    var posX;
    var posY;
    if (event.pageX || event.pageY) {
        posX = event.pageX - canvasLimites.left;
        posY = event.pageY - canvasLimites.top;
    } else {
        posX = event.clientX - canvasLimites.left;
        posY = event.clientY - canvasLimites.top;
    }
    return {x: posX,
        y: posY
    };
};


function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame + '  ');
        stompClient.subscribe('/topic/'+sala, function (data) {
          
            theObject = JSON.parse(data.body);
            var ctx = canvas.getContext('2d');
            ctx.beginPath();
            ctx.arc(theObject["x"], theObject["y"], 1, 0, 2 * Math.PI);
            ctx.stroke();
        });
    });
}
sendPoint = function () {
    stompClient.send("/topic/"+sala, {}, JSON.stringify({x: x, y: y}));
};
erase = function () {
    context.clearRect(0, 0, canvas.width, canvas.height);
};
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

hc = function () {
    flagPaint = !flagPaint;
};

nc = function () {
    flagPaint = !flagPaint;
};



$(document).ready(
        function () {
            sala=sessionStorage.getItem("sala");
            connect();
            console.info('connecting to websockets');
            canvas = document.getElementById('myCanvas');
            context = canvas.getContext('2d');
            context.strokeStyle = '#FFFFFF';
            context.lineWidth = '5';
            context.lineJoin = "round";
            /**canvas.addEventListener('mousedown', hc, false);
            canvas.addEventListener('mouseup', nc, false);
            canvas.addEventListener('mousemove', function (evt) {
                if (flagPaint == false) {

                } else {
                    var mousePos = getMousePos(canvas, evt);
                    x = mousePos.x;
                    y = mousePos.y;
                    sendPoint();

                    //stompClient.send("/app/newpoint", {}, JSON.stringify({x: x, y: y}));
                    var mensaje = 'Position' + mousePos.x + mousePos.y;
                }
            }, false);*/

        }
);
