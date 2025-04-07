package bcc.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

public class Star {
    private double x;
    private double y;
    private double yVelocity;
    private int radius;
    private double brightness;

    public Star() {

        // step 3.1 - initialize the instance variables with random values.
        // The x position should be a random value between 0 and the screen width.
        // The y position should be a random value between 0 and the screen height.
        // The brightness should be a random value between 0 and 1.
        // The rest is up to you!

        
    }

    public void update() {
        // step 3.3 - update the y position of the star by adding the y velocity to it.
        // If the star goes off the bottom of the screen, reset its position to the top (y=0)
        // with a random x position.
        
    }

    public void display(Graphics g) {
        g.setColor(new Color((float) brightness, (float) brightness, (float) brightness));
        g.fillOval((int) x, (int) y, radius, radius);
    }

    //used for testing

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public int getRadius() {
        return radius;
    }

    public double getBrightness() {
        return brightness;
    }
}
