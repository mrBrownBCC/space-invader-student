package bcc.spaceinvaders;

public class SpeedyEnemy extends EnemyShip {

    public SpeedyEnemy(double x, double y, GamePanel game) {
        //step 4.1 - make a call to the super constructor of the EnemyShip class.
        // Use the following parameters:
        // width & height: 65, image = Utilities.SPEEDY_ENEMY_IMAGE, health of 10, shot cooldown of 40
        // after that, set the xVelocity to 2 and yVelocity to 1.
        // Set the rotation to 180 degrees to make the image point down the screen.
        super(x, y, 65, 65, Utilities.SPEEDY_ENEMY_IMAGE, 10, 40,game);
        this.xVelocity = 2; 
        this.yVelocity = 1; 
        this.rotation = 180;
    }

    @Override
    public void update() {
       //step 4.2 - give a 1% chance to flip the xVelocity to make the enemy move in the opposite direction.
       //remember you can use Math.random() to generate a random number between 0 and 1.
       //afterwards,  be sure to call the super.update() method. 
        if(Math.random() < 0.01) {
            xVelocity = -xVelocity; // Randomly change direction
        }
        super.update();
    }

   
}
