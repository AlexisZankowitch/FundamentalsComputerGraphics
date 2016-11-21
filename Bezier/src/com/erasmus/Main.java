package com.erasmus;

public class Main {

    public static void main(String[] args) {
        BezierFrame dialog = new BezierFrame();
        dialog.pack();
        dialog.initializeGraphics();
        dialog.initDrawing();
        dialog.setVisible(true);
        System.exit(0);
    }
}
