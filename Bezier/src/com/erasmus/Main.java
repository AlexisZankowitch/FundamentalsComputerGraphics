package com.erasmus;

public class Main {

    public static void main(String[] args) {
        Transformations dialog = new Transformations();
        dialog.pack();
        dialog.initializeGraphics();
        dialog.initDrawing();
        dialog.setVisible(true);
        System.exit(0);
    }
}
