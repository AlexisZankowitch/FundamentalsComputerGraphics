import java.awt.*;
import java.util.HashMap;


class Line {
    private Point starting;
    private Point ending;
    private HashMap<String,Integer> deltas;
    private HashMap<String,Integer> variation;
    private BresenhamAlgorithm bresenhamAlgorithm;


    Line(Point starting, Point ending, BresenhamAlgorithm bresenhamAlgorithm) {
        this.starting = starting;
        this.ending = ending;

        this.bresenhamAlgorithm = bresenhamAlgorithm;

        this.deltas = new HashMap<>();
        this.deltas.put("x",Math.abs(this.ending.getX()- this.starting.getX()));
        this.deltas.put("y",Math.abs(this.ending.getY()- this.starting.getY()));

        this.variation = new HashMap<>();
        this.variation.put("x",(this.ending.getX() > this.starting.getX())?1:-1);
        this.variation.put("y",(this.ending.getY() > this.starting.getY())?1:-1);
    }

    void drawLine(){
        if(this.deltas.get("x")>=this.deltas.get("y")){
            this.draw();
        }else {
            this.invertLine();
            this.draw();
        }
    }

    private void invertLine(){
        this.starting.revertPoint();
        this.ending.revertPoint();
    }

    private void draw(){
        this.bresenhamAlgorithm.getTextArea1().setText(" ");
        Graphics graphics = this.bresenhamAlgorithm.getjPanel1().getGraphics();
        graphics.clearRect(0,0,this.bresenhamAlgorithm.getjPanel1().getWidth(),this.bresenhamAlgorithm.getjPanel1().getHeight());
        int xn = this.starting.getX();
        int yn = this.starting.getY();
        int pn = 2*this.deltas.get("y")-this.deltas.get("x");
        int n = 0;
        while(xn != this.ending.getX())
        {
            this.bresenhamAlgorithm.getTextArea1().append("\n it°"+ n + " ; x"+n+"= " + xn + " ; y"+n+"= "  + yn + " ; p"+n+ "= " + pn);
            if(pn > 0)
            {
                xn = xn + this.variation.get("x");
                yn = yn + this.variation.get("y");
                pn = pn + 2*this.deltas.get("y") - 2*this.deltas.get("x");
            }
            else
            {
                xn = xn + this.variation.get("x");
                pn = pn + 2*this.deltas.get("y");
            }

            graphics.drawLine(xn, yn, xn, yn);
            n++;
        }
    }

    @Override
    public String toString() {
        return "Line{" +
                "starting=" + starting +
                ", ending=" + ending +
                '}';
    }

    public String completeToString() {
        return "Line{" +
                "starting=" + starting +
                ", ending=" + ending +
                ", deltas=" + deltas +
                ", variation=" + variation +
                '}';
    }
}
