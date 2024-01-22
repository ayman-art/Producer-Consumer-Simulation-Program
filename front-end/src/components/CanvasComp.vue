<!-- eslint-disable vue/no-mutating-props -->

<template>
  <div id="canvas-component">
    <canvas id="canvas"></canvas>
  </div>
</template>

<script>
import Arrow from "../models/arrow.js";
import Machine from "../models/machine.js";
import Queue from "../models/queue.js";
import { ref } from "vue";
const port = 8080;

export default {
  props: {
    selected: String,
  },
  data() {
    return {
      c: "",
      ctx: "",
      selectedShape: null,
      mouseDownState: false,
      startx: 0,
      starty: 0,
      endx: 0,
      endy: 0,
      MorQ: 0,
    };
  },
  methods: {
    getSelectedShape(startx, starty) {
      for (let i = 0; i < this.machines.length; i++) {
        if (this.machines[i].isSelected(startx, starty)) {
          this.selectedShape = this.machines[i];
          this.MorQ = 1;
          return;
        }
      }
      for (let i = 0; i < this.queues.length; i++) {
        if (this.queues[i].isSelected(startx, starty)) {
          this.selectedShape = this.queues[i];
          this.MorQ = 2;
          return;
        }
      }
      this.MorQ = 0;
      this.selectedShape = null;
    },
    mousedown(e) {
      e.preventDefault();
      this.startx = e.x - 10;
      this.starty = e.y - 70;
      if (this.selected == "connection") {
        this.getSelectedShape(this.startx, this.starty);
      }
      this.mouseDownState = true;
    },
    mouseup(e) {
      if (this.mouseDownState) {
        this.endx = e.x - 10;
        this.endy = e.y - 70;
        if (this.selected == "connection") {
          if (this.MorQ == 1) {
            let machine = this.selectedShape;
            this.getSelectedShape(this.endx, this.endy);
            if (this.MorQ == 2) {
              let queue = this.selectedShape;
              if (machine.out == null) {
                machine.out = queue;
                queue.in.push(machine);
                let arrow = new Arrow(machine.x, machine.y, queue.x, queue.y);
                this.connections.push(arrow);
              }
            }
          } else if (this.MorQ == 2) {
            let queue = this.selectedShape;
            this.getSelectedShape(this.endx, this.endy);
            if (this.MorQ == 1) {
              let machine = this.selectedShape;
              queue.out.push(machine);
              machine.in.push(queue);
              let arrow = new Arrow(queue.x, queue.y, machine.x, machine.y);
              this.connections.push(arrow);
            }
          }
        }
        this.drawElements();
      }

      this.mouseDownState = false;
    },
    clear() {
      this.machines = [];
      this.queues = [];
      this.connections = [];
      this.MorQ = 0;
      this.selectedShape = null;
      this.drawElements();
      this.machineIdCount = 0
      this.queueIdCount = 0;
    },
    drawElements() {
      this.ctx.clearRect(0, 0, this.width, this.height);
      for (let i = 0; i < this.connections.length; i++) {
        this.connections[i].draw(this.ctx);
      }
      for (let i = 0; i < this.machines.length; i++) {
        this.machines[i].draw(this.ctx);
      }
      for (let i = 0; i < this.queues.length; i++) {
        this.queues[i].draw(this.ctx);
      }
    },
    clicked(e) {
      const x = e.x - 10;
      const y = e.y - 70;
      if (this.selected == "machine") {
        let machine = new Machine(x, y, this.machineIdCount);
        this.machineIdCount++;
        this.machines.push(machine);

        this.drawElements();
      } else if (this.selected == "queue") {
        let queue = new Queue(x, y, this.queueIdCount);
        this.queueIdCount++;
        this.queues.push(queue);
        this.drawElements();
      }
    },
    updateSystem(newSys) {
      for (let i = 0; i < newSys.machines.length; i++) {
        for (let j = 0; j < this.machines.length; j++) {
          if (newSys.machines[i].id == this.machines[j].id) {
            this.machines[j].colorbg = `#${newSys.machines[i].color}`;
          }
        }
      }
      for (let i = 0; i < newSys.queues.length; i++) {
        for (let j = 0; j < this.queues.length; j++) {
          if (newSys.queues[i].id == this.queues[j].id) {
            this.queues[j].size = newSys.queues[i].count;
          }
        }
      }
      this.drawElements();
    },
    formatSystem() {
      let machineList = [];
      let invalid = false;
      if (this.machines.length == 0) invalid = true;
      for (let i = 0; i < this.machines.length; i++) {
        let newMachine = this.machines[i];
        if (newMachine.in.length == 0 || newMachine.out == null) {
          invalid = true;
          break;
        }
        let inList = [];
        for (let j = 0; j < newMachine.in.length; j++) {
          inList.push(newMachine.in[j].id);
        }
        machineList.push({
          id: newMachine.id,
          in: inList,
          out: newMachine.out.id,
        });
      }
      if (invalid) return null;
      let queueList = [];
      for (let i = 0; i < this.queues.length; i++) {
        queueList.push({
          id: this.queues[i].id,
        });
      }
      return {
        machines: machineList,
        queues: queueList,
      };
    },
  },
  async mounted() {
    this.c = document.getElementById("canvas");
    this.c.addEventListener("mousedown", (e) => this.mousedown(e));
    this.c.addEventListener("mouseup", (e) => this.mouseup(e));
    this.c.addEventListener("click", (e) => this.clicked(e));
    document.body.addEventListener(
      "mouseup",
      () => (this.mouseDownState = false)
    );
    this.ctx = this.c.getContext("2d");
    this.width = window.innerWidth - 20;
    this.height = window.innerHeight - 100;
    this.c.width = this.width;
    this.c.height = this.height;
    this.machines = [];
    this.queues = [];
    this.connections = [];
    this.machineIdCount = 0;
    this.queueIdCount = 0;
    this.MorQ = 0;
  },
  expose: ["clear", "formatSystem", "updateSystem"],
};
</script>

<style scoped>
#canvas {
  width: 100%;
  height: 100%;
  border-radius: 25px;
  border: 2px solid black;
  margin-top: 10px;
}
</style>
