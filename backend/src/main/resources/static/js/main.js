//let ws = new SockJS("http://localhost:8080/ws");
//let stomp = Stomp.over(ws);
//
//stomp.connect({}, onConnected, onError);
//
//function onConnected() {
//    stomp.subscribe('simulate/public');
//    stomp.send('app/start', {}, JSON.stringify({machines: [{id: 2, in: 1, out: 3}], queues: [{id: 1}, {id: 3}]}));
//}
//
//function onError(e) {
//    console.log(e);
//}
const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8080/ws",
});
console.log("H");

stompClient.onConnect = (frame) => {
  console.log("Connected: " + frame);
  stompClient.subscribe("/simulate/public", (e) => {
    console.log(e.body);
  });
};

stompClient.onWebSocketError = (error) => {
  console.error("Error with websocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

function connect() {
  stompClient.activate();
}

function start() {
  stompClient.publish({
    destination: "/app/start",
    body: JSON.stringify({
      machines: [
        { id: 1, in: 0, out: 1 },
        { id: 2, in: 1, out: 3 },
        { id: 3, in: 1, out: 3 },
        { id: 4, in: 0, out: 4 },
        { id: 5, in: 3, out: 5 },
        { id: 6, in: 4, out: 6 },
        { id: 7, in: 5, out: 6 },
      ],
      queues: [
        { id: 0 },
        { id: 1 },
        { id: 3 },
        { id: 4 },
        { id: 5 },
        { id: 6 },
      ],
    }),
  });
}

function stop() {
  stompClient.publish({
    destination: "/app/stop",
  });
}

function replay() {
  stompClient.publish({
    destination: "/app/replay",
  });
}

connect();
