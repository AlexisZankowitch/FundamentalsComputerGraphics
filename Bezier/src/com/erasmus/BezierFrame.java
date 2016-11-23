package com.erasmus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BezierFrame extends JDialog {
    public static JFrame instance;

    private final int[] offset = {-7,-30};

    private Graphics g;
    private ArrayList<Point> points;
    private boolean draw, pointsUpdated, bezier;
    private Thread drawThread;
    private Drawer drawer;
    private MouseCustomListener mouseCustomListener;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel drawablePanel;
    private JButton resetButton;
    private JTextArea drawField;

    public BezierFrame() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.bezier = false;

        points = new ArrayList<Point>();
        buttonOK.addActionListener(e -> onDraw());
        this.mouseCustomListener = new MouseCustomListener();
        drawablePanel.addMouseListener(this.mouseCustomListener);
        drawablePanel.addMouseMotionListener(this.mouseCustomListener);

        buttonOK.addActionListener(e -> onOK());
        resetButton.addActionListener(e -> onReset());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onReset() {
        this.bezier = false;
        this.points.clear();
        this.drawablePanel.repaint();
    }

    public void initDrawing(){
        g.setColor(Color.black);
        this.g.drawLine(10,0,this.drawablePanel.getWidth(),0);
        this.g.drawLine(0,10,0, this.drawablePanel.getHeight());
        this.draw = true;
        this.pointsUpdated = false;
        this.drawer = new Drawer();
        this.drawThread = new Thread(drawer);
        this.drawThread.start();
    }

    private void onOK() {
        // add your code here
        this.drawBezier();
    }

    private void onCancel() {
        // add your code here if necessary
        this.draw = false;
        this.points.add(new Point(10,15));
        this.points.add(new Point(25,20));
        this.points.add(new Point(40,20));
        this.points.add(new Point(25,40));
        this.points.add(new Point(35,90));
        this.points.add(new Point(35,90));
        this.points.add(new Point(35,20));
        this.points.add(new Point(16,40));
        this.points.add(new Point(55,60));
        this.mathematicalCalculation();
        dispose();
    }

    private void onDraw() {
        this.drawBezier();
    }

    private static int factorial(int n)
    {
        if (n == 0) return 1;
        return n * factorial(n-1);
    }

    private float coefBino(int n, int i){
        return factorial(n)/(factorial(i)*factorial(n-i));
    }

    private float bernstein(float t, int n , int i){
        return (float) (coefBino(n,i)*Math.pow(t,i)*Math.pow((1-t),(n-i)));
    }

    private void mathematicalCalculation(){
        DecimalFormat df = new DecimalFormat("#.#####");
        int n = this.points.size()-1;
        int r=0;
        float t = 0.4f;
        System.out.println("t= "+t);
        ArrayList<Float> bs = new ArrayList<Float>();
        for (int i = 0; i <= n; i++) {
            float b = (float) (coefBino(n, i) * Math.pow(t, i) * Math.pow((1 - t), (n - i)));
            bs.add(b);
            System.out.println("B(" + i + "," + n + ")= " + n + "!/" + i + "! (" + n + "-" + i + ")!" + "*" + "t^" + i + " * (1-t" + ")" + "^ (" + n + "-" + i + ") = " + df.format(b));
        }
        System.out.println('\n');
        for (int i = 0; i <= n; i++) {
            System.out.print("P("+i+") = ("+this.points.get(i).getX()+","+this.points.get(i).getY()+")");
        }
        System.out.print('\n'+"X=");
        for (int i = 0; i <= n; i++) {
            System.out.print("B("+i+","+n+")*P("+i+")");
            if(i<n)
                System.out.print(" + ");
        }
        System.out.print('\n'+"=");
        for (int i = 0; i <= n; i++) {
            r += this.points.get(i).getX()*bs.get(i);
            System.out.print(df.format(bs.get(i))+"*"+this.points.get(i).getX());
            if(i<n)
                System.out.print(" + ");
            else
                System.out.print(" = " + r);
        }
        System.out.println('\n');
        System.out.print('\n'+"Y=");
        r=0;
        for (int i = 0; i <= n; i++) {
            System.out.print("B("+i+","+n+")*P("+i+")");
            if(i<n)
                System.out.print(" + ");
        }
        System.out.print('\n'+"=");
        for (int i = 0; i <= n; i++) {
            r += this.points.get(i).getY()*bs.get(i);
            System.out.print( df.format(bs.get(i))+"*"+this.points.get(i).getY());
            if(i<n)
                System.out.print(" + ");
            else
                System.out.print(" = " + r);
        }

        System.out.println('\n');
    }

    private void drawBezier() {
        float t = 0;
        int x, y;
        float b;

        while (t<=1){
            x = this.offset[0];
            y = this.offset[1];
            for (int i = 0; i < this.points.size(); i++) {
                b = bernstein(t,this.points.size() -1,i);
                x += this.points.get(i).getX() * b;
                y += this.points.get(i).getY() * b;
                //draw the point
                g.setColor(Color.black);
            }
            g.drawLine(x,y,x,y);
            t += 0.0001;
        }
        this.bezier = true;
    }

    private void drawPoints() throws InterruptedException {
        this.drawablePanel.repaint();
        Thread.sleep(1);
        for(int i=0; i < this.points.size(); i++){
            this.g.setColor(Color.red);
            if (i == 0)
                this.g.setColor(Color.blue);
            else if (i == this.points.size() - 1)
                this.g.setColor(Color.green);
            g.drawString(
                    String.valueOf(i+1),
                    (int) (this.points.get(i).getX()) + this.offset[0],
                    (int) (this.points.get(i).getY()) + this.offset[1] - 5);
            g.drawOval(
                    (int) (this.points.get(i).getX()) + this.offset[0],
                    (int) (this.points.get(i).getY()) + this.offset[1],
                    6, 6);
        }
        this.pointsUpdated = false;
        if (this.bezier)
            this.drawBezier();
    }

    void initializeGraphics() {
        this.g = this.drawablePanel.getGraphics();
    }

    private int getPoint(int x, int y){
        int t = 10;
        for (int k=0; k<this.points.size(); k++){
            if (points.get(k).getX() > x-t &&
                    points.get(k).getY() > y-t &&
                    points.get(k).getX() < x+t &&
                    points.get(k).getY() < y+t ){
                System.out.println("find");
                return k;
            }
        }
        return -1;
    }

    private class MouseCustomListener implements MouseListener, MouseMotionListener {

        private Point selectedPoint;
        private int x,y;

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.x = e.getX();
            this.y = e.getY();
            System.out.println("click: " + this.x + " "+ this.y);
            if (getPoint(this.x,this.y)<0){
                System.out.println("add");
                points.add(new Point(e.getX(),e.getY()));
            }else {
                System.out.println("find");
                this.selectedPoint = points.get(getPoint(this.x,this.y));
                //unDrawPoint(this.x,this.y);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)){
                System.out.println("release");
                pointsUpdated = true;
            }else{
                this.selectedPoint = points.get(getPoint(this.x,this.y));
                if(points.contains(this.selectedPoint))
                    points.remove(selectedPoint);
                System.out.println("right click");
                pointsUpdated = true;
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (this.selectedPoint != null)
                this.selectedPoint.setLocation(e.getX(),e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    private class Drawer implements Runnable {

        @Override
        public void run() {
            System.out.println("start" + pointsUpdated);
            while (draw){
                try {
                    if(pointsUpdated){
                        drawPoints();
                    }
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("done" + pointsUpdated);
        }
    }
}
