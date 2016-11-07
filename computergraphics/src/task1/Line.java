package task1;

import utilities.GraphicalObject;
import utilities.Point;

import java.util.HashMap;


public class Line extends GraphicalObject{
    private Point starting;
    private Point ending;
    private HashMap<String,Integer> deltas;
    private HashMap<String,Integer> variation;
    private Boolean invert = false;


    public Line(Point starting, Point ending) {
        super();
        this.starting = starting;
        this.ending = ending;
        this.deltas = new HashMap<>();
        this.variation = new HashMap<>();
    }

    public void draw(){
        this.setDeltas();
        this.setVariation();
        if(this.deltas.get("x")>=this.deltas.get("y")){
            this.bresenhamAlgorithm();
        }else {
            this.invertPoint();
            this.setDeltas();
            this.setVariation();
            this.bresenhamAlgorithm();
        }
    }

    private void setDeltas(){
        this.deltas.put("x",Math.abs(this.ending.getX()- this.starting.getX()));
        this.deltas.put("y",Math.abs(this.ending.getY()- this.starting.getY()));
    }

    private void setVariation(){
        this.variation.put("x",(this.ending.getX() > this.starting.getX())?1:(this.ending.getX() == this.starting.getX())?0:-1);
        this.variation.put("y",(this.ending.getY() > this.starting.getY())?1:(this.ending.getY() == this.starting.getY())?0:-1);
    }

    private void invertPoint(){
        this.invert = true;
        this.starting.revertPoint();
        this.ending.revertPoint();
    }

    private void bresenhamAlgorithm(){
        this.jFrame.getTextArea1().setText(" ");
        int xn = this.starting.getX();
        int yn = this.starting.getY();
        int pn = 2*this.deltas.get("y")-this.deltas.get("x");
        int n = 0;
        while(xn != this.ending.getX())
        {
            this.jFrame.getTextArea1().append(
                    "\n it°"+ n + " ; x"+n+"= " +
                    ((invert)?yn:xn) + " ; y"+n+"= "  +
                    ((invert)?xn:yn) + " ; p"+n+ "= " +
                    pn);
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
            if(invert)
                jFrame.getPanel1().getGraphics().drawLine(yn, xn, yn, xn);
            else
                jFrame.getPanel1().getGraphics().drawLine(xn, yn, xn, yn);

            n++;
        }
    }
}
