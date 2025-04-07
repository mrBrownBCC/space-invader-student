package bcc.spaceinvaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PlayerShip extends Ship {

    public PlayerShip(double x, double y, GamePanel game) {
        //step 2.2  A call to super should be made and all instance variables should be initialized. set the image to the player image and set the health to 100.
        //You can access the image using Utilities.PLAYER_NORMAL_IMAGE. All other images will be accessed in the same way.
        //Set the width and height to 70.
        super(x, y, 70,70,Utilities.PLAYER_NORMAL_IMAGE, 100, game);
    }


    @Override
    public void update() {
        //step 2.3  Implement the update method. The player ship should move based on the WASD keys.

       

        //then set the xVelocity and yVelocity to 0
        xVelocity = 0;
        yVelocity = 0;

        //the if statment below checks if the W key is pressed. If it is, set the yVelocity to -Utilities.PLAYER_SPEED, moving the ship up. Similar code 
        //can be used for the other keys using KeyEvent.VK_S, KeyEvent.VK_A, and KeyEvent.VK_D.
        if (Utilities.isKeyPressed(KeyEvent.VK_W)){
            yVelocity = -Utilities.PLAYER_SPEED;
        } 
        //DELETE THESE
        if (Utilities.isKeyPressed(KeyEvent.VK_S)){ 
            yVelocity = Utilities.PLAYER_SPEED;
        }
        if (Utilities.isKeyPressed(KeyEvent.VK_A)){ 
            xVelocity = -Utilities.PLAYER_SPEED;
        }
        if (Utilities.isKeyPressed(KeyEvent.VK_D)){ 
            xVelocity = Utilities.PLAYER_SPEED;
        }
        //now make a call to super.update()
        super.update();
        //step 2.4 make a call to keepInBounds() to make sure the ship does not go off the screen.
        keepInBounds();
    }

    public void keepInBounds() {
        //step 2.4 - make sure that the ship does not go off the screen. Remember to take the ship's width and height into account.
        //The code for preventing the ship from going off the left side of the screen is provided below as an example.  
        //Use Utilities.SCREEN_WIDTH and Utilities.SCREEN_HEIGHT to get the width and height of the screen.
        if (x < 0) x = 0;
        if (x > Utilities.SCREEN_WIDTH - width) x = Utilities.SCREEN_WIDTH - width;
        if (y < 0) y = 0;
        if (y > Utilities.SCREEN_HEIGHT - height) y = Utilities.SCREEN_HEIGHT - height;
    }

    @Override
    public void display(Graphics g) {
        //step 2.5 - First make a call to displayHealthBar() to display the health bar.

        //Then set the rotation of the ship based on the xVelocity. If the xVelocity is less than 0, set the rotation to -15. If it is greater than 0, set it to 15.

        displayHealthBar(g);

        if(xVelocity < 0) rotation = -15;
        else if(xVelocity > 0) rotation = 15;
        else rotation = 0;

        super.display(g);

    }

    @Override
    public void shoot() {
        //step 5.4 - create a projectile and add it to the game using the game.addProjectile method.
        //use PROJECTILE_SIZE for the width and height, and set the xVelocity to 0 and yVelocity to -10.
        //use Utilities.PLAYER_PROJECTILE_IMAGE for the image.
        //set the friendly flag to true.
        Projectile p = new Projectile(x + width / 2 - Utilities.PROJECTILE_SIZE/2 , y + Utilities.PROJECTILE_SIZE, 0, -10, Utilities.PLAYER_PROJECTILE_IMAGE, true, game);
        game.addProjectile(p);
    }

    @Override
    public void handleCollision(GameObject other) {
        //step 5.2 - USE instanceof to check if the other object is a projectile.
        //if it is,  cast the other object to a projectile and check if it is friendly.
        //if it is NOT friendly, take damage from the projectile. 
        //use takeDamage() to take damage from the projectile.
        if(other instanceof Projectile) {
            Projectile projectile = (Projectile) other;
            if(!projectile.isFriendly()) {
                takeDamage(projectile.getDamage());
            }
        }
    }
}
