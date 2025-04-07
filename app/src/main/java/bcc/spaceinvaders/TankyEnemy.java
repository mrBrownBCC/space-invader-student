package bcc.spaceinvaders;
import java.awt.Graphics;
public class TankyEnemy extends EnemyShip {
    
    int dirSwitchTimer = 0;
    int dirSwitchInterval = 100; // Time in milliseconds to switch direction
    public TankyEnemy(double x, double y, GamePanel game) {
        //step 6.1 - make a call to the super constructor of the EnemyShip class.
        // Use 100x100 for the width and height,
        // Utilities.TANK_ENEMY_IMAGE for the image, 30 for health, and 40 for shot cooldown.
        // Set the xVelocity to 2 and yVelocity to 0.5.
        // Set the point value to 20.
        super(x, y, 100, 100, Utilities.TANK_ENEMY_IMAGE,30, 40,game);
        this.xVelocity = 2; 
        this.yVelocity = .5; 
        this.rotation = 180;

        pointValue = 20; // Set point value for TankyEnemy
        //leave this be
        dirSwitchTimer = dirSwitchInterval/2;
    }

    @Override
    public void update() {
        //step 6.2 - if dirSwitchTimer is greater than or equal to dirSwitchInterval, flip the xVelocity.
        //then set dirSwitchTimer to 0. Otherwise, add 1 to dirSwitchTimer.
        //call the super.update() method.

       if(dirSwitchTimer >= dirSwitchInterval) {
            xVelocity = -xVelocity; 
            dirSwitchTimer = 0;
        } else {
            dirSwitchTimer += 1;
        }
        super.update();
    }

    @Override
    public void display(Graphics g) {// no changes needed 
        displayHealthBar(g);
        super.display(g);
    }

    public int getDirSwitchTimer() {
        return dirSwitchTimer;
    }
    public void setDirSwitchTimer(int dirSwitchTimer) {
        this.dirSwitchTimer = dirSwitchTimer;
    }
    public int getDirSwitchInterval() {
        return dirSwitchInterval;
    }
    public void setDirSwitchInterval(int dirSwitchInterval) {
        this.dirSwitchInterval = dirSwitchInterval;
    }
   
}
