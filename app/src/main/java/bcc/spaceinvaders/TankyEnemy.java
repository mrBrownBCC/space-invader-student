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
        super(0,0,0,0,null,0,0,null);
        //leave this be
        dirSwitchTimer = dirSwitchInterval/2;
    }

    @Override
    public void update() {
        //step 6.2 - if dirSwitchTimer is greater than or equal to dirSwitchInterval, flip the xVelocity.
        //then set dirSwitchTimer to 0. Otherwise, add 1 to dirSwitchTimer.
        //call the super.update() method.
    }

    @Override
    public void display(Graphics g) {// no changes needed 
        displayHealthBar(g);
        super.display(g);
    }

    //used for testing
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
