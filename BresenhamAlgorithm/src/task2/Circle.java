package task2;

import utilities.*;
import utilities.Point;

/**
 *
 * Created by zankowitch on 24/10/16.
 *
 */
public class Circle implements GraphicalObject{
    private Point center;
    private int radius;
    private JFrame jFrame;

    public Circle(Point center, Point tangent, JFrame jFrame) {
        this.center = center;
        this.jFrame = jFrame;
        this.radius = (int) Math.round(
                Math.sqrt(
                        Math.pow(
                                tangent.getX()-this.center.getX(),
                                2)
                        +
                        Math.pow(
                                tangent.getY()-this.center.getY(),
                                2)
                )
        );
        this.jFrame.setRadius(this.radius);
        this.drawL();
    }

    public Circle(Point center, int value, JFrame jFrame) {
        this.center = center;
        this.jFrame = jFrame;
        this.radius = value;
        this.drawL();
    }

    private void drawL(){
        jFrame.getPanel1().getGraphics().drawLine(
                this.center.getX()-this.radius,
                this.center.getY(),
                this.center.getX()+this.radius,
                this.center.getY()
        );
        jFrame.getPanel1().getGraphics().drawLine(
                this.center.getX(),
                this.center.getY()-this.radius,
                this.center.getX(),
                this.center.getY()+this.radius
        );
    }

    @Override
    public void draw() {
        int xn = 0;
        int yn = this.radius;
        int pn = 1 - this.radius;


        while (xn <= yn){
            if (pn<0){
                xn++;
                pn = pn +2*xn;
            }else {
                xn++;
                yn--;
                pn = pn +2*xn - 2*yn;
            }
            jFrame.getPanel1().getGraphics().drawLine(
                    xn + this.center.getX(), yn +this.center.getY(),
                    xn + this.center.getX(), yn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    yn +this.center.getX(), xn + this.center.getY(),
                    yn + this.center.getX(), xn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    yn +this.center.getX(), -1 * xn + this.center.getY(),
                    yn + this.center.getX(), -1 * xn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    xn + this.center.getX(), -1 * yn +this.center.getY(),
                    xn + this.center.getX(), -1 * yn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    -1 * xn + this.center.getX(), -1 * yn +this.center.getY(),
                    -1 * xn + this.center.getX(), -1 * yn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    -1 * yn +this.center.getX(), -1 * xn + this.center.getY(),
                    -1 * yn + this.center.getX(), -1 * xn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    -1 * yn +this.center.getX(), xn + this.center.getY(),
                    -1 * yn + this.center.getX(), xn + this.center.getY()
            );
            jFrame.getPanel1().getGraphics().drawLine(
                    -1 * xn + this.center.getX(), yn +this.center.getY(),
                    -1 * xn + this.center.getX(), yn + this.center.getY()
            );
        }
    }
}
