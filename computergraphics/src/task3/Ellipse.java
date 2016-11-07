package task3;

import utilities.GraphicalObject;
import utilities.Point;
import java.awt.*;

public class Ellipse extends GraphicalObject {

    private Point center;
    private int radiusX;
    private int radiusY;
    private int n;

    public Ellipse(Point center) {
        this.center = center;
        this.radiusX = (int) jFrame.getRadiusX().getValue();
        this.radiusY = (int) jFrame.getRadiusY().getValue();
    }

    public Ellipse(Point topLeft, Point bottomRight){
        g.setColor(Color.red);
        int widthRect = euclideanDistance(topLeft,new Point(bottomRight.getX(),topLeft.getY()));
        int heightRect = euclideanDistance(new Point(bottomRight.getX(),topLeft.getY()),bottomRight);
        g.drawRect(
                (topLeft.getX()<bottomRight.getX())?topLeft.getX():bottomRight.getX(),
                (topLeft.getY()<bottomRight.getY())?topLeft.getY():bottomRight.getY(),
                widthRect,
                heightRect
        );
        this.radiusX = widthRect/2;
        this.radiusY = heightRect/2;
        this.radiusY = heightRect/2;
        this.center = new Point(
                (topLeft.getX()<bottomRight.getX())?topLeft.getX() + this.radiusX:topLeft.getX() - this.radiusX,
                (topLeft.getY()<bottomRight.getY())?topLeft.getY() + this.radiusY:topLeft.getY() - this.radiusY);
        this.center.draw();
    }

    private void drawL(){
        g.setColor(Color.black);
        g.drawLine(
                this.center.getX()-this.radiusX,
                this.center.getY(),
                this.center.getX()+this.radiusX,
                this.center.getY()
        );
        g.drawLine(
                this.center.getX(),
                this.center.getY()-this.radiusY,
                this.center.getX(),
                this.center.getY()+this.radiusY
        );
        g.setColor(Color.black);
    }

    @Override
    public void draw() {
        drawL();
        int rXSquare = (int) Math.pow(this.radiusX,2);
        int rYSquare = (int) Math.pow(this.radiusY,2);
        xn = 0;
        yn = this.radiusY;
        n = 0;
        //part 1
        pn = rYSquare - rXSquare * this.radiusY + rXSquare/4;

        while (rYSquare * xn < rXSquare * yn){
            if(pn<0){
                xn++;
                pn = pn + 2 * rYSquare * xn + rYSquare;
            }else {
                xn++;
                yn--;
                pn = pn + 2 * rYSquare * xn + rYSquare - 2 * rXSquare*yn;
            }
            // additions : move to the right position
            drawEllipseQuarter(Color.red);
            n++;
        }
        //part 2
        pn = rYSquare * (int) Math.pow(xn+0.5,2) + rXSquare * (int) Math.pow(yn-1,2) - rXSquare * rYSquare;
        while (yn>0){
            if (pn>0){
                yn--;
                pn = pn - 2 * rXSquare * yn + rXSquare;
            }else {
                yn--;xn++;
                pn = pn - 2 * rXSquare * yn + rXSquare + 2 * rYSquare * xn;
            }
            drawEllipseQuarter(Color.blue);
            n++;
        }
    }

    private void drawEllipseQuarter(Color color){
        g.setColor(color);
        g.drawLine(
                xn+this.center.getX(),
                yn+this.center.getY(),
                xn+this.center.getX(),
                yn+this.center.getY()
        );
        g.drawLine(
                xn+this.center.getX(),
                -1* yn+this.center.getY(),
                xn+this.center.getX(),
                -1 * yn+this.center.getY()
        );
        g.drawLine(
                -1 * xn+this.center.getX(),
                yn+this.center.getY(),
                -1 * xn+this.center.getX(),
                yn+this.center.getY()
        );
        g.drawLine(
                -1 * xn+this.center.getX(),
                -1 * yn+this.center.getY(),
                -1 * xn+this.center.getX(),
                -1 * yn+this.center.getY()
        );
    }
}
