package task3;

import utilities.GraphicalObject;
import utilities.Point;

import java.awt.*;

public class Ellipse extends GraphicalObject {

    private Point center;
    private int radiusX;
    private int radiusY;

    public Ellipse(Point center) {
        int w = jFrame.getPanel1().getWidth();
        int h = jFrame.getPanel1().getHeight();
        this.center = center;
        int xC = w/2;
        int yC = h/2;

        //draw axis
        g.drawLine(0,yC,w,yC);
        g.drawLine(xC,0,xC,h);

        this.radiusX = (int) jFrame.getRadiusX().getValue();
        this.radiusY = (int) jFrame.getRadiusY().getValue();
    }

    @Override
    public void draw() {
        int rXSquare = (int) Math.pow(this.radiusX,2);
        int rYSquare = (int) Math.pow(this.radiusY,2);
        int xn = 0;
        int yn = this.radiusY;
        //part 1
        float pn = rYSquare - rXSquare * this.radiusY + (1/4) * rXSquare;
        while (rYSquare * xn <= rXSquare * yn){
            if(pn<0){
                xn++;
                pn = pn + 2 * rYSquare * xn + rYSquare;
            }else {
                xn++;
                yn--;
                pn = pn + 2 * rYSquare * xn + rYSquare - 2 * rXSquare*yn;
            }
            // additions : move to the right position
            g.setColor(Color.red);
            g.drawLine(
                    xn+this.center.getX(),
                    yn+this.center.getY(),
                    xn+this.center.getX(),
                    yn+this.center.getY()
            );
            //// TODO: 07/11/16 symmetric points
        }
        //part 2
        pn = rYSquare * (int) Math.pow(xn+1/2,2) + rXSquare * (int) Math.pow(yn-1,2) - rXSquare * rYSquare;
        while (yn>0){
            if (pn>0){
                yn--;
                pn = pn - 2 * rXSquare * yn + rXSquare;
            }else {
                yn--;xn++;
                pn = pn - 2 * rXSquare * yn + rXSquare + 2 * rYSquare * xn;
            }
            g.setColor(Color.blue);
            g.drawLine(xn+this.center.getX(),yn+this.center.getY(),xn+this.center.getX(),yn+this.center.getY());
        }
    }

    //// TODO: 07/11/16 Additional task: draw ellipse inside a rectangle. Use mouse to define rectangle (TL-BR)
}
