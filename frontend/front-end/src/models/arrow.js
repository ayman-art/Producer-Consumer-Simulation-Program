export default class Arrow{
    x1
    y1
    x2
    y2
    t
    constructor(xstart,ystart, xend, yend, tip=0.9){
        this.x1 = xstart;
        this.y1 = ystart;
        this.x2 = xend;
        this.y2 = yend;
        this.t = tip;
    }
    draw(context){
        const arrow = {
            dx: this.x2 - this.x1,
            dy: this.y2 - this.y1
        };
          const middle = {
            x: arrow.dx * this.t + this.x1,
            y: arrow.dy * this.t + this.y1
        };
        const tip = {
            dx: this.x2 - middle.x,
            dy: this.y2 - middle.y
        };
        context.beginPath();
        context.moveTo(this.x1, this.y1);
        context.lineTo(middle.x, middle.y);
          context.moveTo(middle.x + 0.5 * tip.dy, middle.y - 0.5 * tip.dx);
        context.lineTo(middle.x - 0.5 * tip.dy, middle.y + 0.5 * tip.dx);
        context.lineTo(this.x2, this.y2);
        context.closePath();
        context.stroke();
    }
}