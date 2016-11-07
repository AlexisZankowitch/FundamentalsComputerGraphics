package utilities;

import java.awt.*;

public abstract class GraphicalObject {

    protected int xn,yn;
    protected float pn;
    protected JFrame jFrame;
    protected Graphics g;

    public GraphicalObject(){
        this.jFrame = JFrame.getInstance();
        this.g = this.jFrame.getPanel1().getGraphics();
    }

    public abstract void draw();

    protected int euclideanDistance(Point a, Point b){
        return (int) Math.round(
            Math.sqrt(
                Math.pow(b.getX()-a.getX(), 2)
                    +
                Math.pow(b.getY()-a.getY(), 2)
            )
        );
    }

}
