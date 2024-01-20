export default class Queue{
    id
    x
    y
    in = []
    out= []
    size = 0
    constructor(x,y, id){
        this.x = x;
        this.y= y;
        this.id = id;
    }
    draw(context) {
        context.beginPath()
        context.strokeStyle = '#000000'
        startx = this.x - 50
        starty = this.y - 25
        width = this.x + 50 - startx
        height = this.y + 25 - starty
        context.rect(startx, starty, width, height)
        context.stroke()
        context.font = "15px Arial";
        context.fillText(`Q${this.id}: size:${this.size}`, this.x, this.y);
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