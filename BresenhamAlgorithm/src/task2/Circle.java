package task2;

import utilities.*;
import utilities.Point;

/**
 * Created by zankowitch on 24/10/16.
 */
public class Circle implements GraphicalObject{
    private Point center;
    private Point tangent;
    private int radius;
    private JFrame jFrame;

    public Circle(Point center, Point tangent, JFrame jFrame) {
        this.center = center;
        this.tangent = tangent;
        this.jFrame = jFrame;

        this.drawL();
    }

    private void drawL(){
        jFrame.getPanel1().getGraphics().drawLine(0,0,jFrame.getPanel1().getWidth(),jFrame.getPanel1().getHeight());
        jFrame.getPanel1().getGraphics().drawLine(0,jFrame.getPanel1().getHeight(),jFrame.getPanel1().getWidth(),0);
        jFrame.getPanel1().getGraphics().drawLine(0,jFrame.getPanel1().getHeight()/2,jFrame.getPanel1().getWidth(),jFrame.getPanel1().getHeight()/2);
        jFrame.getPanel1().getGraphics().drawLine(jFrame.getPanel1().getWidth()/2,0,jFrame.getPanel1().getWidth()/2,jFrame.getPanel1().getHeight());
    }

    @Override
    public void draw() {
        int R = (int) Math.round(Math.sqrt(Math.pow(this.center.getX()-this.tangent.getX(),2)+Math.pow(this.center.getY()-this.tangent.getY(),2)));
        System.out.println(R);

        int xn = 0;
        int yn = R;
        int pn = 1 - R;

        while (xn <= yn){
            if (pn<0){
                xn++;
                pn = pn +2*xn;
            }else {
                xn++;
                yn--;
                pn = pn +2*xn - 2*yn;
            }
            //todo draw symmetrically the others 7 segments  
            jFrame.getPanel1().getGraphics().drawLine(
                    xn + this.center.getX(),
                    yn +this.center.getY(),
                    xn + this.center.getX(),
                    yn + this.center.getY());
        }
    }
}
