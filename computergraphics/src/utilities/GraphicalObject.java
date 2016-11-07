package utilities;

import java.awt.*;

public abstract class GraphicalObject {

    protected int xn,yn,pn;
    protected JFrame jFrame;
    protected Graphics g;

    public GraphicalObject(){
        this.jFrame = JFrame.getInstance();
        this.g = this.jFrame.getPanel1().getGraphics();
    }

    public abstract void draw();
}
