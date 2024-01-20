<script>
import CanvasComp from './components/CanvasComp.vue'
import { ref } from 'vue'
import { Client } from '@stomp/stompjs'
  export default {
    data(){
      return {
        port : 8080,
        selected: ref("none"),
        snapshots : ref([])
      }
    },
    setup() {
      const stompClient = new Client({
        brokerURL: 'ws://localhost:8080/ws'
      })

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

      

      connect()

      //return { snapshots, start, stop, replay }
    },
    components:{
      CanvasComp
    },
    methods:{
      start() {
        stompClient.publish({
          destination: '/app/start',
          body: JSON.stringify({
            machines: [
              { id: 1, in: 0, out: 1 },
              { id: 2, in: 1, out: 3 },
              { id: 3, in: 1, out: 3 },
              { id: 4, in: 0, out: 4 },
              { id: 5, in: 3, out: 5 },
              { id: 6, in: 4, out: 6 },
              { id: 7, in: 5, out: 6 }
            ],
            queues: [{ id: 0 }, { id: 1 }, { id: 3 }, { id: 4 }, { id: 5 }, { id: 6 }]
          })
        })
      },
      stop(){
        stompClient.publish({
          destination: '/app/stop'
        })
      },
      replay(){
        stompClient.publish({
          destination: '/app/replay'
        })
      }
    }
  }
</script>


<template>
  <div id="container">
    <DialogComp
      v-if="showDialog"
      :requirements="requirements"
      :default-values="defaultValues"
      @changeParameters="changeParameters"
    />
    <div id="toolbox">
      <div id="tool-container">
        <li>
          <div class="tool" @click="selected = 'connection'">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/up-right-arrow.png" alt="up-right-arrow"/>
          </div>
        </li>
        <li>
          <div class="tool" @click="selected = 'machine'">
            <img width="25" 
            height="25" 
            src="https://img.icons8.com/ios/50/circled-m.png" alt="circled-m"/>
          </div>
        </li>
        <li>
          <div class="tool" @click="selected = 'queue'">
            <img
              width="25"
              height="25"
              src="https://img.icons8.com/material-sharp/24/rectangle-stroked.png"
              alt="rectangle-stroked"
            />
          </div>
        </li>
        <li>
          <div class="tool" id="clear">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/broom.png" alt="broom" />
          </div>
        </li>
        <li>
          <div class="tool" id="start">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/start--v1.png" alt="start--v1"/>
          </div>
        </li>
        <li>
          <div class="tool" id="stop">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/stop-squared.png" alt="stop-squared"/>
          </div>
        </li>
        <li>
          <div class="tool" id="replay">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/memories.png" alt="memories"/>
          </div>
        </li>
      </div>
      <div id="active-box">
        <span>{{ selected }}</span>
      </div>
    </div>
    <div>
      <CanvasComp :selected="selected" @open-dialog="openDialog" ref="applyChangesRef" />
    </div>
  </div>
</template>


<style scoped>
#toolbox {
  height: 50px;
  border-radius: 24px;
  width: 100%;
  background-color: rgb(161, 21, 35);
}
#active-box {
  float: right;
  margin-right: 50px;
  padding: 10px 10px 10px 10px;
  height: 20px;
  margin-top: 3px;
  border-radius: 5px;
  border: 2px solid black;
}
#tool-container {
  margin-left: 24px;
}
li {
  float: left;
  list-style-type: none;
}
.tool {
  height: 10%;
  padding: 10px;
}
.tool:hover {
  background-color: rgb(153, 11, 18);
}
.selected {
  background-color: rgb(87, 87, 87);
}
</style>
