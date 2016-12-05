package com.erasmus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Transformations extends JDialog {
    public static JFrame instance;

    //Offset are mandatory, don't know why.... :o
    private final int[] offset = {-7,-30};

    private Thread drawThread;
    private Graphics g;
    private ArrayList<Point> points;
    private ArrayList<Point> tempPoints;
    private Point rotateCoord;
    private boolean draw, pointsUpdated, drawed, pointsTransform, rotationPoint=false;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel drawablePanel;
    private JButton resetButton;
    private JRadioButton bezierRadioButton;
    private JRadioButton lineRadioButton;
    private JTextField xTextField;
    private JTextField yTextField;
    private JButton moveButton;
    private JTextField aTextField;
    private JButton scaleButton;
    private JSlider rotateSlider;
    private JLabel degreeValue;
    private JButton rotateButton;
    private JTextField bTextField;
    private JButton defineRotationPointButton;
    private JTextArea drawField;

    Transformations() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.drawed = false;

        points = new ArrayList<Point>();
        this.tempPoints = new ArrayList<>();
        buttonOK.addActionListener(e -> onDraw());
        MouseCustomListener mouseCustomListener = new MouseCustomListener();
        drawablePanel.addMouseListener(mouseCustomListener);
        drawablePanel.addMouseMotionListener(mouseCustomListener);

        resetButton.addActionListener(e -> onReset());
        buttonCancel.addActionListener(e -> onCancel());
        rotateSlider.addChangeListener(e -> onRotateSlider());
        rotateButton.addActionListener(e -> onRotateButton());
        defineRotationPointButton.addActionListener(e -> onDefineRotationPoint());
        moveButton.addActionListener(e -> onMove());
        scaleButton.addActionListener(e -> onScale());

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

    private void onDefineRotationPoint() {
        this.rotationPoint = true;
    }

    private void onReset() {
        this.drawed = false;
        this.rotationPoint = false;
        this.pointsTransform = false;
        this.rotateSlider.setValue(0);
        this.pointsTransform = true;
        this.tempPoints.clear();
        this.points.clear();
        this.drawablePanel.repaint();
    }

    private void onScale() {
        try {
            int a = Integer.parseInt(this.aTextField.getText());
            int b = Integer.parseInt(this.bTextField.getText());
            for (Point point: this.points)
            {
                point.x *= a;
                point.y *= b;
            }
            this.pointsUpdated = true;
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    private void onMove() {
        try {
            int moveX = Integer.parseInt(this.xTextField.getText());
            int moveY = Integer.parseInt(this.yTextField.getText());
            for (Point point: this.points)
            {
                point.x += moveX;
                point.y += moveY;
            }
            this.pointsUpdated = true;
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    private void onRotateSlider() {
        this.degreeValue.setText((String.valueOf(this.rotateSlider.getValue())));
        if(this.pointsTransform) {
            try {
                int xCenter;
                int yCenter;
                if (rotationPoint){
                    xCenter = rotateCoord.x;
                    yCenter = rotateCoord.y;
                } else{
                    xCenter = this.drawablePanel.getWidth() / 2;
                    yCenter = this.drawablePanel.getHeight() / 2;
                }

                int bufferX, bufferY;
                double a = Math.toRadians(this.rotateSlider.getValue());
                tempPoints.clear();
                for (Point point : this.points) {
                    bufferX = (int) Math.round((point.x - xCenter) * Math.cos(a) - (point.y - yCenter) * Math.sin(a) + xCenter);
                    bufferY = (int) Math.round((point.x - xCenter) * Math.sin(a) + (point.y - yCenter) * Math.cos(a) + yCenter);
                    tempPoints.add(new Point(bufferX, bufferY));
                }
                drawThosePoints(tempPoints);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private void onRotateButton() {
        this.pointsTransform = false;
        this.rotateSlider.setValue(0);
        this.points = this.copyList(this.tempPoints, this.points);
        this.pointsUpdated = true;
        this.pointsTransform = true;
        this.rotationPoint = false;
    }

    private ArrayList<Point> copyList(ArrayList<Point> from, ArrayList<Point> to)
    {
        to.clear();
        to.addAll(from);
        return to;
    }

    void initDrawing(){
        g.setColor(Color.black);
        this.g.drawLine(10,0,this.drawablePanel.getWidth(),0);
        this.g.drawLine(0,10,0, this.drawablePanel.getHeight());
        this.draw = true;
        this.pointsUpdated = false;
        this.pointsTransform = true;
        Drawer drawer = new Drawer();
        drawThread = new Thread(drawer);
        drawThread.start();
    }

    private void onCancel() {
        this.draw = false;
        dispose();
    }

    private void onDraw() {
        this.drawRep();
        if (this.bezierRadioButton.isSelected())
            this.drawBezier(points);
        if (this.lineRadioButton.isSelected())
            this.drawFigure(this.points);
    }

    private void drawThosePoints(ArrayList<Point> points)
    {
        if (this.bezierRadioButton.isSelected())
            this.drawBezier(points);
        if (this.lineRadioButton.isSelected())
            this.drawFigure(points);
    }

    private void drawRep() {
        g.setColor(Color.GRAY);
        g.drawLine(this.offset[0], drawablePanel.getHeight()/2+this.offset[1], drawablePanel.getWidth(), drawablePanel.getHeight()/2+this.offset[1]);
        g.drawLine(drawablePanel.getWidth()/2+this.offset[0], this.offset[1]+this.offset[1], drawablePanel.getWidth()/2+this.offset[0], drawablePanel.getHeight()+this.offset[1]);
    }

    //issue when n too big
    private static long factorial(int n)
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

    private void drawFigure(ArrayList<Point> points)
    {
        g.setColor(Color.BLACK);
        int x, y, x2, y2;
        for (int i=0; i<points.size(); i++)
        {
            x = points.get(i).x + this.offset[0];
            y = points.get(i).y + this.offset[1];
            if (i != points.size()-1)
            {
                x2 = points.get(i+1).x + this.offset[0];
                y2 = points.get(i+1).y + this.offset[1];
            }
            else
            {
                x2 = points.get(0).x + this.offset[0];
                y2 = points.get(0).y + this.offset[1];
            }
            g.drawLine(x, y, x2, y2);
        }
        this.drawed = true;
    }

    private void drawBezier(ArrayList<Point> points) {
        float t = 0;
        int x, y;
        float b;
        while (t<=1){
            x = this.offset[0];
            y = this.offset[1];
            for (int i = 0; i < points.size(); i++) {
                b = bernstein(t,points.size() -1,i);
                x += points.get(i).getX() * b;
                y += points.get(i).getY() * b;
                g.setColor(Color.black);
            }
            g.drawLine(x,y,x,y);
            t += 0.0001;
        }
        this.drawed = true;
    }

    private void drawPoint(ArrayList<Point> pointsToDraw) throws InterruptedException {
        for(int i=0; i < pointsToDraw.size(); i++){
            this.g.setColor(Color.red);
            if (i == 0)
                this.g.setColor(Color.blue);
            else if (i == pointsToDraw.size() - 1)
                this.g.setColor(Color.green);
            g.drawString(
                    String.valueOf(i+1),
                    (int) (pointsToDraw.get(i).getX()) + this.offset[0],
                    (int) (pointsToDraw.get(i).getY()) + this.offset[1] - 5);
            this.drawPoint(pointsToDraw.get(i));
        }
        this.pointsUpdated = false;
        if (this.drawed)
            this.onDraw();
    }

    private void drawPoint(Point point) {
        g.drawOval(
                (int) (point.getX()) + this.offset[0],
                (int) (point.getY()) + this.offset[1],
                6, 6);
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
            if (!rotationPoint){
                if (getPoint(this.x,this.y)<0){
                    System.out.println("add");
                    points.add(new Point(e.getX(),e.getY()));
                }else {
                    System.out.println("find");
                    this.selectedPoint = points.get(getPoint(this.x,this.y));
                    //unDrawPoint(this.x,this.y);
                }
            }else {
                pointsUpdated = false;
                rotateCoord = new Point(this.x, this.y);
                drawPoint(rotateCoord);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!rotationPoint){
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
                drawRep();
                try {
                    if(pointsUpdated && !rotationPoint){
                        drawablePanel.repaint();
                        Thread.sleep(1);
                        drawRep();
                        drawPoint(points);
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
