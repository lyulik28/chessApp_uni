const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:1111/chat-websocket'
});

let currentUser;

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    currentUser = frame.headers['user-name'];
    var subscription = '/topic/' + document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    console.log(subscription);
    stompClient.subscribe(subscription, (greeting) => {
        showGreeting(JSON.parse(greeting.body).text);
    });

    var gameSubscription = '/topic/game/' + document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    stompClient.subscribe(gameSubscription, (moveResult) => {
        var result = JSON.parse(moveResult.body);
        showBoard(result);
    });

    sendConnect();
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}


window.onload = function() {
    stompClient.activate();
};

window.onunload = function() {
    stompClient.deactivate();
    setConnected(false);
};

function sendConnect() {
    var destination = "/app/chat/"+document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    stompClient.publish({
        destination: destination,
        body: JSON.stringify({'text': currentUser+" connected "})
    });

    var destination = "/app/startGame/"+document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    stompClient.publish({
        destination: destination,
        body: JSON.stringify({'text': currentUser})
    });
}
function sendText() {
    var destination = "/app/chat/"+document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    stompClient.publish({
        destination: destination,
        body: JSON.stringify({'text': currentUser+": "+$("#text").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#send" ).click(() => sendText());
});