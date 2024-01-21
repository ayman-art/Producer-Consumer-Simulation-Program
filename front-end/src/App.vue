<script>
import CanvasComp from './components/CanvasComp.vue'
import { ref } from 'vue'
import { Client } from '@stomp/stompjs'
  export default {
    data(){
      return {
        port : 8080,
        selected: ref("none"),
        selectedShape: ref(null),
        snapshots : ref([]),
        canvas : ref(null)
      }
    },
    setup() {
      const stompClient = new Client({
        brokerURL: 'ws://localhost:8080/ws'
      })
      let snapshots = ref([])
      stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/simulate/public', (e) => {
          console.log(e.body)
          this.$refs.canvasRef.updateSystem(e.body)
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
      return {stompClient, snapshots}
    },
    components:{
      CanvasComp
    },
    methods:{
      clearCanvas(){
        this.$refs.canvasRef.clear()
      },
      startSimulation(){
        let outputObject = this.$refs.canvasRef.formatSystem()
        if(outputObject == null) {
          alert("Please submit a valid system")
        }else{
          console.log(outputObject)
          this.start(outputObject)
        }
      },
      stopSimulation(){
        this.stop()
      },
      replaySimulation(){
        this.replay()
      },
      start(o) {
        this.stompClient.publish({
          destination: '/app/start',
          body: JSON.stringify(o)
        })
      },
      stop(){
        this.stompClient.publish({
          destination: '/app/stop'
        })
      },
      replay(){
        this.stompClient.publish({
          destination: '/app/replay'
        })
      }
    },
    mounted(){
    }
  }
</script>


<template>
  <div id="container">
    
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
          <div class="tool" @click="clearCanvas" id="clear">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/broom.png" alt="broom" />
          </div>
        </li>
        <li>
          <div class="tool" @click="startSimulation" id="start">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/start--v1.png" alt="start--v1"/>
          </div>
        </li>
        <li>
          <div class="tool" @click="stopSimulation" id="stop">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/stop-squared.png" alt="stop-squared"/>
          </div>
        </li>
        <li>
          <div class="tool" @click="replaySimulation" id="replay">
            <img width="25" height="25" src="https://img.icons8.com/ios/50/memories.png" alt="memories"/>
          </div>
        </li>
      </div>
      <div id="active-box">
        <span>{{ selected }}</span>
      </div>
    </div>
    <div>
      <CanvasComp :selected="selected" ref="canvasRef" />
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
