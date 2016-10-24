package utilities;

/**
 * Created by zank on 10/10/16.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void revertPoint(){
        int a=this.y;
        this.y=this.x;
        this.x=a;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "utilities.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
