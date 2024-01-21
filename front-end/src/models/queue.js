export default class Queue{
    id
    x
    y
    in 
    out
    constructor(x,y, id){
        this.x = x;
        this.y= y;
        this.in = []
        this.out = []
        this.size = 0
        this.id = id
    }
    draw(context) {
        context.fillStyle = "black"
        context.strokeStyle = "black"
        context.font = "15px Arial";
        context.fillText(`Q${this.id}: size:${this.size}`, this.x-28, this.y+6);
        context.beginPath()
        let startx = this.x - 50
        let starty = this.y - 25
        let width = this.x + 50 - startx
        let height = this.y + 25 - starty
        context.rect(startx, starty, width, height)
        context.stroke()
    }
    isSelected(x, y) {
        if (
            x >= Math.min(this.x-50, this.x+50) &&
            x <= Math.max(this.x-50, this.x+50) &&
            y >= Math.min(this.x-25, this.x+25) &&
            y <= Math.max(this.x-25, this.x+25)
        )
        return true
        else return false
    }
}