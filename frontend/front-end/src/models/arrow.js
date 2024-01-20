export default class Arrow{
    x1
    y1
    x2
    y2
    t
    constructor(x1,y1, x2, y2, t=0.9){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.t = t;
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
        context.moveTo(x1, y1);
        context.lineTo(middle.x, middle.y);
          context.moveTo(middle.x + 0.5 * tip.dy, middle.y - 0.5 * tip.dx);
        context.lineTo(middle.x - 0.5 * tip.dy, middle.y + 0.5 * tip.dx);
        context.lineTo(tihs.x2, this.y2);
        context.closePath();
        context.stroke();
    }
}