package bcc.spaceinvaders;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Test5 {
    
    private GamePanel gamePanel;
    private BufferedImage tempImage;
    private Graphics tempGraphics;
    
    @Before
    public void setUp() {
        System.out.println("Setting up collision and damage test environment...");
        gamePanel = new GamePanel(true);
        
        // Create a temporary image and graphics object for testing display methods
        tempImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        tempGraphics = tempImage.getGraphics();
        
        // Clear any existing game objects
        gamePanel.getGameObjects().clear();
    }
    
    @After
    public void tearDown() {
        if (tempGraphics != null) {
            tempGraphics.dispose();
        }
    }
    
    // Test 1: Verify collision detection works correctly
    @Test
    public void testCollisionDetection() {
        // Create two overlapping objects
        GameObject obj1 = new GameObject(100, 100, 50, 50, null, gamePanel);
        GameObject obj2 = new GameObject(125, 125, 50, 50, null, gamePanel);
        
        // They should collide
        assertTrue("Overlapping objects should collide", obj1.checkCollision(obj2));
        assertTrue("Collision detection should be symmetric", obj2.checkCollision(obj1));
        
        // Create two non-overlapping objects
        GameObject obj3 = new GameObject(200, 200, 50, 50, null, gamePanel);
        GameObject obj4 = new GameObject(300, 300, 50, 50, null, gamePanel);
        
        // They should not collide
        assertFalse("Non-overlapping objects should not collide", obj3.checkCollision(obj4));
        assertFalse("Collision detection should be symmetric", obj4.checkCollision(obj3));
        
        // Test that an object doesn't collide with itself
        assertFalse("Object should not collide with itself", obj1.checkCollision(obj1));
        
        // Test that dead objects don't collide
        Ship deadShip = new Ship(100, 100, 50, 50, null, 0, gamePanel);
        assertFalse("Dead objects should not register collisions", deadShip.checkCollision(obj1));
        assertFalse("Dead objects should not register collisions", obj1.checkCollision(deadShip));
    }
    
    // Test 2: Verify PlayerShip takes damage from enemy projectiles but not friendly ones
    @Test
    public void testPlayerShipDamage() {
        PlayerShip player = new PlayerShip(100, 100, gamePanel);
        int initialHealth = player.health;
        
        // Create an enemy projectile
        Projectile enemyProjectile = new Projectile(100, 100, 0, 0, null, false, gamePanel);
        enemyProjectile.handleCollision(player); // This won't do damage since handleCollision is one-way
        player.handleCollision(enemyProjectile); // This should do damage
        
        assertTrue("Player should take damage from enemy projectile", player.health < initialHealth);
        
        // Reset player health
        player.health = initialHealth;
        
        // Create a friendly projectile
        Projectile friendlyProjectile = new Projectile(100, 100, 0, 0, null, true, gamePanel);
        player.handleCollision(friendlyProjectile);
        
        assertEquals("Player should not take damage from friendly projectile", initialHealth, player.health);
    }
    
    // Test 3: Verify Ship isAlive returns false when health <= 0
    @Test
    public void testShipIsAlive() {
        Ship ship = new Ship(100, 100, 50, 50, null, 10, gamePanel);
        
        assertTrue("Ship should be alive with positive health", ship.isAlive());
        
        ship.takeDamage(5);
        assertTrue("Ship should be alive with positive health after damage", ship.isAlive());
        
        ship.takeDamage(5);
        assertFalse("Ship should not be alive with zero health", ship.isAlive());
        
        ship.takeDamage(5);
        assertFalse("Ship should not be alive with negative health", ship.isAlive());
    }
    
    // Test 4: Verify PlayerShip shoot method creates a friendly projectile
    @Test
    public void testPlayerShipShooting() {
        PlayerShip player = new PlayerShip(100, 100, gamePanel);
        ArrayList<GameObject> gameObjects = gamePanel.getGameObjects();
        
        // Clear any existing objects
        gameObjects.clear();
        
        // Make player shoot
        player.shoot();
        
        // Verify a projectile was added
        boolean projectileFound = false;
        Projectile foundProjectile = null;
        
        for (GameObject obj : gameObjects) {
            if (obj instanceof Projectile) {
                projectileFound = true;
                foundProjectile = (Projectile) obj;
                break;
            }
        }
        
        assertTrue("PlayerShip shoot should add a projectile to the game", projectileFound);
        
        // Check projectile properties
        assertNotNull("Projectile should be created", foundProjectile);
        assertTrue("Player projectile should be friendly", foundProjectile.isFriendly());
        
        // Check projectile is positioned correctly and moving upward
        assertEquals(player.getX() + player.getWidth()/2 - Utilities.PROJECTILE_SIZE/2, 
                   foundProjectile.getX(), 1.0);
        assertTrue("Player projectile should move upward", foundProjectile.yVelocity < 0);
    }
    
    // Test 5: Verify EnemyShip takes damage from friendly projectiles but not from enemy ones
    @Test
    public void testEnemyShipDamage() {
        EnemyShip enemy = new SpeedyEnemy(100, 100, gamePanel);
        int initialHealth = enemy.health;
        
        // Create a friendly projectile
        Projectile friendlyProjectile = new Projectile(100, 100, 0, 0, null, true, gamePanel);
        enemy.handleCollision(friendlyProjectile);
        
        assertTrue("Enemy should take damage from friendly projectile", enemy.health < initialHealth);
        
        // Reset enemy health
        enemy.health = initialHealth;
        
        // Create an enemy projectile
        Projectile enemyProjectile = new Projectile(100, 100, 0, 0, null, false, gamePanel);
        enemy.handleCollision(enemyProjectile);
        
        assertEquals("Enemy should not take damage from enemy projectile", initialHealth, enemy.health);
    }
    
    // Test 6: Verify projectiles are marked as not alive when they hit appropriate ships
    @Test
    public void testProjectileCollisionLiveness() {
        PlayerShip player = new PlayerShip(100, 100, gamePanel);
        EnemyShip enemy = new SpeedyEnemy(200, 200, gamePanel);
        
        // Create a friendly projectile hitting an enemy
        Projectile friendlyProjectile = new Projectile(200, 200, 0, 0, null, true, gamePanel);
        
        // Initially the projectile should be alive
        assertTrue("Projectile should be alive initially", friendlyProjectile.isAlive());
        
        // After collision with enemy, the projectile should be dead
        friendlyProjectile.handleCollision(enemy);
        assertFalse("Friendly projectile should die after hitting enemy", friendlyProjectile.isAlive());
        
        // Create an enemy projectile hitting a player
        Projectile enemyProjectile = new Projectile(100, 100, 0, 0, null, false, gamePanel);
        
        // Initially the projectile should be alive
        assertTrue("Enemy projectile should be alive initially", enemyProjectile.isAlive());
        
        // After collision with player, the projectile should be dead
        enemyProjectile.handleCollision(player);
        assertFalse("Enemy projectile should die after hitting player", enemyProjectile.isAlive());
        
        // Create projectiles hitting the wrong type of ship
        Projectile friendlyHittingPlayer = new Projectile(100, 100, 0, 0, null, true, gamePanel);
        Projectile enemyHittingEnemy = new Projectile(200, 200, 0, 0, null, false, gamePanel);
        
        friendlyHittingPlayer.handleCollision(player);
        enemyHittingEnemy.handleCollision(enemy);
        
        assertTrue("Friendly projectile should remain alive after hitting player", 
                friendlyHittingPlayer.isAlive());
        assertTrue("Enemy projectile should remain alive after hitting enemy", 
                enemyHittingEnemy.isAlive());
    }
    
    // Test 7: Verify projectiles die when they go off screen
    @Test
    public void testProjectileOffScreenDeath() {
        // Create a projectile inside screen
        Projectile projectile = new Projectile(100, 100, 0, 0, null, true, gamePanel);
        assertTrue("Projectile on screen should be alive", projectile.isAlive());
        
        // Test all four screen boundaries
        // Left boundary
        projectile = new Projectile(-50, 100, 0, 0, null, true, gamePanel);
        assertFalse("Projectile off left edge should not be alive", projectile.isAlive());
        
        // Right boundary
        projectile = new Projectile(Utilities.SCREEN_WIDTH + 50, 100, 0, 0, null, true, gamePanel);
        assertFalse("Projectile off right edge should not be alive", projectile.isAlive());
        
        // Top boundary
        projectile = new Projectile(100, -50, 0, 0, null, true, gamePanel);
        assertFalse("Projectile off top edge should not be alive", projectile.isAlive());
        
        // Bottom boundary
        projectile = new Projectile(100, Utilities.SCREEN_HEIGHT + 50, 0, 0, null, true, gamePanel);
        assertFalse("Projectile off bottom edge should not be alive", projectile.isAlive());
    }
    
    // Test 8: Verify completedLevel returns true when all enemies are defeated
    @Test
    public void testLevelCompletion() {
        // Create a game panel with a clear game objects list
        ArrayList<GameObject> gameObjects = gamePanel.getGameObjects();
        gameObjects.clear();
        
        // Add a player
        PlayerShip player = new PlayerShip(100, 100, gamePanel);
        gameObjects.add(player);
        
        // With only the player, level should be completed
        assertTrue("Level should be completed when no enemies exist", gamePanel.completedLevel());
        
        // Add an enemy
        EnemyShip enemy = new SpeedyEnemy(200, 200, gamePanel);
        gameObjects.add(enemy);
        
        // Now level should not be completed
        assertFalse("Level should not be completed when enemies exist", gamePanel.completedLevel());
        
        // Remove the enemy and add a projectile
        gameObjects.remove(enemy);
        Projectile projectile = new Projectile(300, 300, 0, 0, null, true, gamePanel);
        gameObjects.add(projectile);
        
        // Level should be completed (only player and projectile, no enemies)
        assertTrue("Level should be completed with projectiles but no enemies", gamePanel.completedLevel());
    }
}