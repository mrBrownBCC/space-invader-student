package bcc.spaceinvaders;
import java.awt.Graphics;
public class Boss extends EnemyShip {
    
    public Boss(double x, double y, GamePanel game) {
        //step 6.4 - make a call to the super constructor of the EnemyShip class.
        // use 200x200 for the width and height,
        // Utilities.BOSS_IMAGE for the image, 100 for health, and 30 for shot cooldown.
        // Set the xVelocity to 4 and yVelocity to 1.
        // Set the point value to 100.
        super(0,0,0,0,null,0,0,null);
    }

    @Override
    public void update() {
       //step 6.6 - set the yVelocity to 0 if the y position is greater than 0.
       //this will stop the boss from moving down after a certain height.
       //no code for xVelocity is needed. Handled by inheritance! 
       //make sure to call super.update() at the end of this method.
      
    }

    @Override
    public void shoot() {
        // Boss shoots a different projectile!
        Projectile p = new GreenProjectile(x + width / 2 - Utilities.PROJECTILE_SIZE/2, y + height - Utilities.PROJECTILE_SIZE/2, game);
        game.addProjectile(p);
    }
   
    @Override
    public void display(Graphics g) {
        displayHealthBar(g);
        super.display(g);
        // Additional display logic for TankyEnemy can be added here
    }
}
