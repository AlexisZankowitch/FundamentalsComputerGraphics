package utilities;

public abstract class GraphicalObject {

    protected int xn,yn,pn;
    protected JFrame jFrame;

    public GraphicalObject(){
        this.jFrame = JFrame.getInstance();
    }
    public abstract void draw();
}
