import SockJS from 'sockjs-client';

export default class WebSocketService {
  constructor() {
    this.socket = null;
    this.stompClient = null;
  }

  connect(url, onMessageCallback) {
    this.socket = new SockJS(url);
    this.stompClient = Stomp.over(this.socket);

    this.stompClient.connect({}, (frame) => {
      console.log('Connected to WebSocket');
      
      // Subscribe to a specific topic if needed
      // this.stompClient.subscribe('/topic/someTopic', (message) => {
      //   onMessageCallback(JSON.parse(message.body));
      // });
    });
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
      console.log('Disconnected from WebSocket');
    }
  }
}