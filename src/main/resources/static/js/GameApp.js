/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


game = (function () {
    var gameid;
    var username;

    var stompClient = null;

    var subscribe = function () {
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe("/topic/winner." + gameid, function (data) {


            });
        });
    }

    return {
        connect: function () {
            gameid = $("#topic").val();
            $.ajax({
                url: "/pictureci/creategame/" + gameid,
                type: "POST"
            });
            subscribe();
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };
})();