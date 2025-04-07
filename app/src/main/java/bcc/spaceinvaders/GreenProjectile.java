package bcc.spaceinvaders;

import java.awt.Graphics;


public class GreenProjectile extends Projectile {
  public GreenProjectile(double x, double y, GamePanel game) {
    super(x, y, 0, 10, Utilities.BOSS_PROJECTILE_IMAGE, false, game);
  }

  @Override
  public void update() {
    //step 6.7 - make a call to the super.update() method.
    //check if the player's x position is greater than the projectile's x position + width.
    //if it is, add .3 to the xVelocity.
    //if the player's x position plus width is less than the projectile's x position, subtract .3 from the xVelocity.
    //this will make the projectile follow the player.
    //to get the player's x position, use game.getPlayer().getX() and game.getPlayer().getWidth() to get the player's width. 
    super.update();
    if (game.getPlayer().getX() > x + width) {
      System.out.println("xVelocity: " + xVelocity + " " +  game.getPlayer().getX() + " " + x);
      xVelocity += .3;
    } else if (game.getPlayer().getX() + game.getPlayer().getWidth() < x) {
      xVelocity -= .3;
    }
  }

  @Override
  public void display(Graphics g) {
    this.rotation += 5;
    super.display(g);
  }
}
