<template>
  <div id="app">
    <div class="content">
      <p v-for="s in snapshots" :key="s">
        {{ s }}
      </p>
    </div>

    <div class="btns">
      <button @click="start">Start</button>
      <button @click="stop">Stop</button>
      <button @click="replay">Replay</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Client } from '@stomp/stompjs'

export default {
  setup() {
    const stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws'
    })
    const snapshots = ref([])

    stompClient.onConnect = (frame) => {
      console.log('Connected: ' + frame)
      stompClient.subscribe('/simulate/public', (e) => {
        snapshots.value.push(e.body)
        console.log(e.body)
      })
    }

    stompClient.onWebSocketError = (error) => {
      console.error('Error with websocket', error)
    }

    stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message'])
      console.error('Additional details: ' + frame.body)
    }

    const connect = () => {
      stompClient.activate()
    }

    const start = () => {
      stompClient.publish({
        destination: '/app/start',
        body: JSON.stringify({
          machines: [
            { id: 1, in: [0], out: 1 },
            { id: 2, in: [1], out: 3 },
            { id: 3, in: [1], out: 3 },
            { id: 4, in: [0], out: 4 },
            { id: 5, in: [3], out: 5 },
            { id: 6, in: [4, 5], out: 6 },
            { id: 7, in: [4, 5], out: 6 }
          ],
          queues: [{ id: 0 }, { id: 1 }, { id: 3 }, { id: 4 }, { id: 5 }, { id: 6 }]
        })
      })
    }

    const stop = () => {
      stompClient.publish({
        destination: '/app/stop'
      })
    }

    const replay = () => {
      stompClient.publish({
        destination: '/app/replay'
      })
    }

    connect()

    return { snapshots, start, stop, replay }
  }
}
</script>
