package com.erasmus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Temp {
    public int centerX, centerY;

    public Temp(JPanel panel, ArrayList<Point> points)
    {
        centerX = panel.getWidth()/2;
        centerY = panel.getHeight()/2;

        //rotation
        double alfa = Math.toRadians(30.0);
        for (Point point : points)
        {
            point.x = (int) Math.round(point.x * Math.cos(alfa) - point.y * Math.sin(alfa));
            point.y = (int) Math.round(point.x * Math.sin(alfa) + point.y * Math.cos(alfa));
        }

    }
}
