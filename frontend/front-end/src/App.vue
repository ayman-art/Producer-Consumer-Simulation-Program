

<template>
  <div id="username-page">
    <div class="username-page-container">
        <h1 class="title">Type your username to enter the Chatroom</h1>
        <form id="usernameForm" name="usernameForm">
            <div class="form-group">
                <input type="text" id="name" placeholder="Username" autocomplete="off" class="form-control" />
            </div>
            <div class="form-group">
                <div  @click="connectWebSocket" class="accent username-submit">Start Chatting</div>
            </div>
        </form>
    </div>
</div>

<div id="chat-page" class="hidden">
    <div class="chat-container">
        <div class="chat-header">
            <h2>Spring WebSocket Chat Demo - By Alibou</h2>
        </div>
        <div class="connecting">
            Connecting...
        </div>
        <ul id="messageArea">

        </ul>
        <form id="messageForm" name="messageForm">
            <div class="form-group">
                <div class="input-group clearfix">
                    <input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control"/>
                    <div  @click="handleWebSocketMessage" class="primary">Send</div>
                </div>
            </div>
        </form>
    </div>
</div>
</template>
<script>
import WebSocketService from './services/WebSocketService';

export default {
  data() {
    return {
      webSocketService: new WebSocketService(),
    };
  },
  mounted() {
    this.connectWebSocket();
  },
  beforeDestroy() {
    this.disconnectWebSocket();
  },
  methods: {
    connectWebSocket() {
      this.webSocketService.connect();
    },
    disconnectWebSocket() {
      this.webSocketService.disconnect();
    },
    handleWebSocketMessage(message) {
      console.log('Received WebSocket message:', message);
      // Handle the received message as needed
    },
  },
};
</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
