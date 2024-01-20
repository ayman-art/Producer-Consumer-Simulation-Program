<script>
import { ref } from 'vue'
import CanvasComp from './components/CanvasComp.vue'

  export default {
    data(){
      return {
        port : 8080,
        selected: ref("none")
      }
    },
    components:{
      CanvasComp
    },
    methods:{

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
          <div class="tool" @click="selected = 'line'">
            <img
              width="25"
              height="25"
              src="https://img.icons8.com/ios/50/line--v1.png"
              alt="line--v1"
            />
          </div>
        </li>
        <li>
          <div class="tool" @click="selected = 'square'">
            <img width="25" 
            height="25" 
            src="https://img.icons8.com/ios/50/circled-m.png" alt="circled-m"/>
          </div>
        </li>
        <li>
          <div class="tool" @click="selected = 'rectangle'">
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
#file-selector {
  float: left;
  margin-top: 13px;
}
#color-selector {
  float: left;
  margin-top: 10px;
}
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
