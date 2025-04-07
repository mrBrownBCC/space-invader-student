
package bcc.spaceinvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class GameObject {
    protected double x, y;
    protected double xVelocity, yVelocity;
    protected Image image;
    protected int width, height;
    protected double rotation;
    protected GamePanel game;

    public GameObject(double x, double y, int width, int height, Image image, GamePanel game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.rotation = 0;
        this.game = game;
    }

    public void update() {
        x += xVelocity;
        y += yVelocity;
    }

    public void display(Graphics g) {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        
        transform.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.setTransform(new AffineTransform()); // Reset to identity transform


        //uncomment to see hit boxes
        
        //g2d.setColor(java.awt.Color.GREEN);
        //g2d.drawRect((int) x, (int) y, width, height);
    }

    public boolean isAlive() {
        return true;
    }

    public double getX() {
        return x;
    }
    

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public double getXVelocity() {
        return xVelocity;
    }
    public double getYVelocity() {
        return yVelocity;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }
    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    public double getRotation() {
        return rotation;
    }

    public boolean containsPoint(double pointX, double pointY) {
        return (pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height);
    }
    public boolean checkCollision(GameObject other) {
        //step 5.1
        //check that the other object is not self
        //check if either object is alive
        //check if either object is not alive
        if(other == this || !isAlive() || !other.isAlive()) {
            return false; // No collision with itself
        }
        //check 4 corners of other object, see if any are inside this object using the containsPoint method above. 
        return containsPoint(other.getX(), other.getY()) ||
               containsPoint(other.getX() + other.getWidth(), other.getY()) ||
               containsPoint(other.getX(), other.getY() + other.getHeight()) ||
               containsPoint(other.getX() + other.getWidth(), other.getY() + other.getHeight());
    }

    public void handleCollision(GameObject other) {
        // Default implementation does nothing
    }
}