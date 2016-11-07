package task2;

import utilities.*;
import utilities.Point;

import java.awt.*;

/**
 *
 * Created by zankowitch on 24/10/16.
 *
 */
public class Circle extends GraphicalObject{
    private Point center;
    private int radius;

    public Circle(Point center, Point tangent) {
        super();
        this.center = center;
        this.radius = euclideanDistance(this.center,tangent);
        this.jFrame.setRadius(this.radius);
    }

    public Circle(Point center, int value) {
        super();
        this.center = center;
        this.radius = value;
    }

    private void drawL(){
        g.setColor(Color.red);
        g.drawLine(
                this.center.getX()-this.radius,
                this.center.getY(),
                this.center.getX()+this.radius,
                this.center.getY()
        );
        g.drawLine(
                this.center.getX(),
                this.center.getY()-this.radius,
                this.center.getX(),
                this.center.getY()+this.radius
        );
        g.setColor(Color.black);
    }

    @Override
    public void draw() {
        this.drawL();
        this.xn = 0;
        this.yn = this.radius;
        this.pn = 1 - this.radius;
        this.jFrame.getTextArea1().setText(" ");

        while (xn <= yn){
            if (pn<0){
                xn++;
                pn = pn +2*xn;
            }else {
                xn++;
                yn--;
                pn = pn +2*xn - 2*yn;
            }
            g.drawLine(
                    xn + this.center.getX(), yn +this.center.getY(),
                    xn + this.center.getX(), yn + this.center.getY()
            );
            g.drawLine(
                    yn +this.center.getX(), xn + this.center.getY(),
                    yn + this.center.getX(), xn + this.center.getY()
            );
            g.drawLine(
                    yn +this.center.getX(), -1 * xn + this.center.getY(),
                    yn + this.center.getX(), -1 * xn + this.center.getY()
            );
            g.drawLine(
                    xn + this.center.getX(), -1 * yn +this.center.getY(),
                    xn + this.center.getX(), -1 * yn + this.center.getY()
            );
            g.drawLine(
                    -1 * xn + this.center.getX(), -1 * yn +this.center.getY(),
                    -1 * xn + this.center.getX(), -1 * yn + this.center.getY()
            );
            g.drawLine(
                    -1 * yn +this.center.getX(), -1 * xn + this.center.getY(),
                    -1 * yn + this.center.getX(), -1 * xn + this.center.getY()
            );
            g.drawLine(
                    -1 * yn +this.center.getX(), xn + this.center.getY(),
                    -1 * yn + this.center.getX(), xn + this.center.getY()
            );
            g.drawLine(
                    -1 * xn + this.center.getX(), yn +this.center.getY(),
                    -1 * xn + this.center.getX(), yn + this.center.getY()
            );
            this.jFrame.getTextArea1().append(
                    "\n it°"+ xn +
                            " ; x"+xn+"= " + Math.addExact(xn,this.center.getX()) +
                            " ; y"+xn+"= "  + Math.addExact(yn,this.center.getY()) +
                            " ; p"+xn+ "= "+ pn
            );
        }
    }
}
