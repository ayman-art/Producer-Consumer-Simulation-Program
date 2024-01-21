export default class Machine{
    id 
    x
    y
    in = []
    out
    colorbg 
    constructor(x,y, id){
        this.x = x;
        this.y= y;
        this.id = id
        this.in = []
        this.colorbg = "#ffffff"
    }
    draw(context, color = "#ffffff") {
        this.colorbg = (color == "#ffffff") ? this.colorbg : color
        context.beginPath()
        context.strokeStyle = '#000000'
        context.fillStyle = this.colorbg
    
        context.arc(this.x, this.y, 20, 0, 2 * Math.PI)
        context.fill()
        context.stroke()
        context.fillStyle = "black"
        context.font = "15px Arial";
        context.fillText(`M${this.id}`, this.x-8, this.y+6);
        
      }
      isSelected(x, y) {
        const dist = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2))
        if (dist <= this.radius) return true
        else return false
      }
}