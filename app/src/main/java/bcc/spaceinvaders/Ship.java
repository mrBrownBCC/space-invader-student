
package bcc.spaceinvaders;

import java.awt.Image;

import java.awt.Graphics;
public class Ship extends GameObject {
    protected int health;
    protected int maxHealth;
    public Ship(double x, double y, int width, int height, Image image, int health, GamePanel game) {
        //step 2.1  A call to super should be made and all instance variables should be initialized. Set health and maxHealth to the same value. 

        super(x, y, width,height, image, game);
        this.health = health;
        this.maxHealth = health;
    }

    public boolean isAlive() {
        //step 5.3 - return true if heath is greater than 0 and false otherwise.
        return health > 0;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }

    public void shoot(){
        //implement in subclasses
    }

    public void displayHealthBar(Graphics g){
        int healthBarWidth = 40;
        int healthBarHeight = 8;
        int healthBarX = (int) x + (width - healthBarWidth) / 2;
        int healthBarY = (int) y  + height;

        g.setColor(java.awt.Color.RED);
        g.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

        g.setColor(java.awt.Color.GREEN);
        g.fillRect(healthBarX, healthBarY, (int) ((double) health / maxHealth * healthBarWidth), healthBarHeight);
    }
    public int getHealth() {
        return health;
    }
}
