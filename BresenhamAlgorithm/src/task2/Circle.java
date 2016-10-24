package task2;

import utilities.*;


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

        int w = jFrame.getjPanel1().getWidth();
        int h = jFrame.getjPanel1().getHeight();

        jFrame.getjPanel1().getGraphics().drawLine(0,0,w,h);
        jFrame.getjPanel1().getGraphics().drawLine(0,h,w,0);
        jFrame.getjPanel1().getGraphics().drawLine(0,h/2,w,h/2);
        jFrame.getjPanel1().getGraphics().drawLine(w/2,0,w/2,h);

        int Xc = w/2;
        int Yc = h/2;
        int R = 100;

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
            jFrame.getjPanel1().getGraphics().drawLine(xn + Xc,yn +Yc ,xn + Xc,yn +Yc);
        }
    }

    @Override
    public void draw() {
        System.out.println("aze");
    }
}
