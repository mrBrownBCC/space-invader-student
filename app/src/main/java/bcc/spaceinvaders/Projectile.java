package bcc.spaceinvaders;

import java.awt.Image;
public class Projectile extends GameObject {
    private boolean friendly;
    private boolean alive;
    private int damageAmount;

    public Projectile(double x, double y, double xVelocity, double yVelocity, Image image, boolean friendly, GamePanel game) {
        //step 4.4 - make a call to the super constructor of the GameObject class.
        // Use Utilities.PROJECTILE_SIZE for the width and height.
        //initialize damageAmount to 10.
        
        super(0,0,0,0,null,null);
    }


    public boolean isAlive() {
        //step 5.7 - check if the projectile is off the screen. if it is, set alive to false.
        //then return the current value of alive. Remember that alive can also be set to false if the projectile is destroyed.
        
        return true;
    }

    public void handleCollision(GameObject other) {
        //step 5.6 - if frienyly and colliding with an enemy ship, set alive to false
        //if not friendly and colliding with a player ship, set alive to false
        //note that alive is an instance variable of the Projectile class.
        
    }
    
    public boolean isFriendly() {
        return friendly;
    }

    public int getDamage() {
        return damageAmount;
    }
}
