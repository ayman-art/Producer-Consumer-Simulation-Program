<!-- eslint-disable vue/no-mutating-props -->

<template>
  <div id="canvas-component">
    <canvas id="canvas"></canvas>
  </div>
</template>

<script>
//import { Line, Rectangle, Triangle, Circle, Square, Ellipse } from '../models/shapes.js'
import  Arrow  from '../models/arrow.js'
import  Machine  from '../models/machine.js'
import  Queue  from '../models/queue.js'
import { ref } from 'vue'
const port = 8080

export default {
  props: {
    selected: String,

  },
  data() {
    return {
      c: '',
      ctx: '',
      shapes: [],
      selectedShape: null,
      mouseDownState: false,
      startx: 0,
      starty: 0,
      endx: 0,
      endy: 0,
      machines: ref([]),
      queues: ref([]),
      connections: ref([]),
    }
  },
  methods: {
    getSelectedShape(startx, starty) {
      for (let i = this.shapes.length - 1; i >= 0; i--) {
        if (this.shapes[i].isSelected(startx, starty)) {
          this.selectedShape = this.shapes[i]
          return
        }
      }
      this.selectedShape = null
    },
    mousedown(e) {
      e.preventDefault()
      this.startx = e.x - 10
      this.starty = e.y - 70
      if (this.selected == 'move') {
        this.getSelectedShape(this.startx, this.starty)
      }
      this.mouseDownState = true
    },
    mouseup(e) {
      if (this.mouseDownState) {
        this.endx = e.x - 10
        this.endy = e.y - 70
        if (this.selected == 'line') this.createLine(this.startx, this.starty, this.endx, this.endy)
        else if (this.selected == 'square') this.createSquare(this.startx, this.starty, this.endx)
        else if (this.selected == 'rectangle')
          this.createRectangle(this.startx, this.starty, this.endx, this.endy)
        else if (this.selected == 'circle')
          this.createCircle(this.startx, this.starty, this.endx, this.endy)
        else if (this.selected == 'ellipse')
          this.createEllipse(this.startx, this.starty, this.endx, this.endy)
        else if (this.selected == 'triangle')
          this.createTriangle(this.startx, this.starty, this.endx, this.endy)
        else if (this.selected == 'move')
          if (this.selectedShape)
            this.moveShape(this.selectedShape, this.startx, this.starty, this.endx, this.endy)
      }
      this.mouseDownState = false
    },
    drawElements(){
      this.ctx.clearRect(0,0, this.width, this.height)
      for(let connection in this.connections) {
        connection.draw()
      }
      for(let machine in this.machines){
        machine.draw()
      }
      for(let queue in this.queues){
        queue.draw()
      }

    },
    clicked(e) {
      const x = e.x - 10
      const y = e.y - 70
      if (this.selected == 'machine') {
        let machine = new Machine(x, y)
        this.machines.push(machine)
        this.drawElements()
      } else if (this.selected == 'queue') {
        let queue = new Queue(x, y)
        this.queues.push(queue)
        this.drawElements()
      }
    },
  },
  expose: [],
  async mounted() {
    this.c = document.getElementById('canvas')
    this.c.addEventListener('mousedown', (e) => this.mousedown(e))
    this.c.addEventListener('mouseup', (e) => this.mouseup(e))
    this.c.addEventListener('click', (e) => this.clicked(e))
    // const undo = document.getElementById('undo')
    // undo.addEventListener('click', () => this.undo())
    // const redo = document.getElementById('redo')
    // redo.addEventListener('click', () => this.redo())
    // const clear = document.getElementById('clear')
    // clear.addEventListener('click', () => this.clear())
    document.body.addEventListener('mouseup', () => (this.mouseDownState = false))
    this.ctx = this.c.getContext('2d')
    this.width = window.innerWidth - 20
    this.height = window.innerHeight - 100
    this.c.width = this.width
    this.c.height = this.height
    //await this.getShapes()
    //this.drawShapes()
  }
}
</script>

<style scoped>
#canvas {
  width: 100%;
  height: 550px;
  border-radius: 25px;
  border: 2px solid black;
  margin-top: 10px;
}
</style>
