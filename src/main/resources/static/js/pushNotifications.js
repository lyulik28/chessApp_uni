const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:1111/chat-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    var subscription = '/topic/' + document.getElementById("userName").textContent;
    console.log(subscription);
    stompClient.subscribe(subscription, (hello) => {
        showGreeting(JSON.parse(hello.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

window.onload = function() {
    stompClient.activate();
};

window.onunload = function() {
    stompClient.deactivate();
    setConnected(false);
};

function setConnected(connected) {
    $("#notification").hide();
}

function showGreeting(message) {
    console.log(message)
    $("#notification").show();
    $("#notification").prepend("<div>" + message.sender + " invites you</div>");
    document.getElementById("gameCode").value = message.gameCode;
}