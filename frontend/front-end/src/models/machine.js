export default class Machine{
    id
    x
    y
    in = []
    out
    colorbg = "#ffffff"
    constructor(x,y, id){
        this.x = x;
        this.y= y;
        this.id = id;
    }
    draw(context, color = "#ffffff") {
        context.beginPath()
        context.strokeStyle = '#000000'
        this.colorbg = (color != null)? color : this.colorbg
        context.fillStyle = color
    
        context.arc(this.x, this.y, 20, 0, 2 * Math.PI)
        context.stroke()
        context.font = "30px Arial";
        context.fillText(`M${this.id}`, this.x, this.y);
      }
      isSelected(x, y) {
        const dist = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2))
        if (dist <= this.radius) return true
        else return false
      }
}