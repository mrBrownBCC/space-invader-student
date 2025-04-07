package bcc.spaceinvaders;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Test4 {
    
    private GamePanel gamePanel;
    private BufferedImage tempImage;
    private Graphics tempGraphics;
    
    @Before
    public void setUp() {
        System.out.println("Setting up enemy and projectile test environment...");
        gamePanel = new GamePanel(true);
        
        // Create a temporary image and graphics object for testing display methods
        tempImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        tempGraphics = tempImage.getGraphics();
    }
    
    @After
    public void tearDown() {
        if (tempGraphics != null) {
            tempGraphics.dispose();
        }
    }
    
    // Test 1: Verify SpeedyEnemy constructor initializes values correctly
    @Test
    public void testSpeedyEnemyConstructor() {
        double x = 150;
        double y = 250;
        
        SpeedyEnemy enemy = new SpeedyEnemy(x, y, gamePanel);
        
        // Test positioning
        assertEquals(x, enemy.getX(), 0.001);
        assertEquals(y, enemy.getY(), 0.001);
        
        // Test dimensions
        assertEquals(65, enemy.getWidth(), 0.01);
        assertEquals(65, enemy.getHeight(), 0.01);
        
        // Test velocities
        assertEquals(2.0, enemy.xVelocity, 0.001);
        assertEquals(1.0, enemy.yVelocity, 0.001);
        
        // Test health
        assertEquals(10, enemy.health);
        
        // Test rotation
        assertEquals(180, enemy.rotation, 0.001);
    }
    
    // Test 2: Verify SpeedyEnemy update method moves the enemy correctly
    @Test
    public void testSpeedyEnemyUpdate() {
        double x = 200;
        double y = 300;
        
        SpeedyEnemy enemy = new SpeedyEnemy(x, y, gamePanel);
        double initialXVelocity = enemy.xVelocity;
        
        // Test normal movement (store original position)
        enemy.update();
        
        // Ship should move according to velocity
        assertEquals(x + initialXVelocity, enemy.getX(), 0.001);
        assertEquals(y + enemy.yVelocity, enemy.getY(), 0.001);
        
        // Test behavior when reaching left wall
        enemy = new SpeedyEnemy(-1, 100, gamePanel);
        initialXVelocity = enemy.xVelocity;
        enemy.update();
        
        // Should bounce back from wall
        assertTrue("Should bounce from wall with positive velocity", enemy.xVelocity > 0);
        
        // Test behavior when reaching right wall
        enemy = new SpeedyEnemy(Utilities.SCREEN_WIDTH, 100, gamePanel);
        initialXVelocity = enemy.xVelocity;
        enemy.update();
        
        // Should bounce back from wall
        assertTrue("Should bounce from wall with negative velocity", enemy.xVelocity < 0);
        
        // Test behavior when going below screen
        enemy = new SpeedyEnemy(300, Utilities.SCREEN_HEIGHT + 10, gamePanel);
        enemy.update();
        
        // Should wrap back to top
        assertEquals(-100.0, enemy.getY(), 2);
    }
    
    // Test 3: Verify SpeedyEnemy randomly changes direction (probabilistic)
    @Test
    public void testSpeedyEnemyRandomDirectionChange() {
        SpeedyEnemy enemy = new SpeedyEnemy(300, 300, gamePanel);
        
        // Run update many times to increase probability of direction change
        boolean directionChanged = false;
        for (int i = 0; i < 10000 && !directionChanged; i++) {
            // Save current velocity
            double currentVelocity = enemy.xVelocity;
            
            // Reset position to avoid hitting walls
            enemy.x = 300;
            enemy.y = 300;
            
            // Update and check if direction changed
            enemy.update();
            
            if (Math.abs(currentVelocity + enemy.xVelocity) < 0.001) {
                directionChanged = true;
            }
        }
        
        assertTrue("SpeedyEnemy should randomly change direction", directionChanged);
    }
    
    // Test 4: Verify GamePanel.beginLevel(0) creates and adds SpeedyEnemies
    @Test
    public void testBeginLevelCreatesEnemies() {
        GamePanel panel = new GamePanel(true);
        
        // Use getter instead of reflection
        ArrayList<GameObject> gameObjects = panel.getGameObjects();
        
        // Clear any existing objects
        gameObjects.clear();
        
        // Start level 0
        panel.beginLevel(0);
        
        // Count SpeedyEnemy instances
        int speedyEnemyCount = 0;
        for (GameObject obj : gameObjects) {
            if (obj instanceof SpeedyEnemy) {
                speedyEnemyCount++;
            }
        }
        
        assertEquals("Level 0 should create 3 SpeedyEnemy instances", 3, speedyEnemyCount);
    }
    
    // Test 5: Verify Projectile constructor initializes values correctly
    @Test
    public void testProjectileConstructor() {
        double x = 100;
        double y = 200;
        double xVel = 5;
        double yVel = -10;
        Image image = Utilities.PLAYER_PROJECTILE_IMAGE;
        boolean friendly = true;
        
        Projectile projectile = new Projectile(x, y, xVel, yVel, image, friendly, gamePanel);
        
        // Test positioning and movement
        assertEquals(x, projectile.getX(), 0.001);
        assertEquals(y, projectile.getY(), 0.001);
        assertEquals(xVel, projectile.xVelocity, 0.001);
        assertEquals(yVel, projectile.yVelocity, 0.001);
        
        // Test dimensions
        assertEquals(Utilities.PROJECTILE_SIZE, projectile.getWidth(), 0.01);
        assertEquals(Utilities.PROJECTILE_SIZE, projectile.getHeight(), 0.01);
        
        // Test friendly state
        assertTrue("Projectile should be marked as friendly", projectile.isFriendly());
        
        // Test with enemy projectile
        Projectile enemyProjectile = new Projectile(x, y, xVel, yVel, image, false, gamePanel);
        assertFalse("Projectile should be marked as not friendly", enemyProjectile.isFriendly());
    }
    
    // Test 6: Verify EnemyShip shoot method creates a projectile
    @Test
    public void testEnemyShipShoot() {
        double x = 150;
        double y = 200;
        
        // Create enemy
        EnemyShip enemy = new SpeedyEnemy(x, y, gamePanel);
        
        // Use getter instead of reflection
        ArrayList<GameObject> gameObjects = gamePanel.getGameObjects();
        
        // Clear any existing objects
        gameObjects.clear();
        
        // Make enemy shoot
        enemy.shoot();
        
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
        
        assertTrue("EnemyShip shoot should add a projectile to the game", projectileFound);
        
        // Check projectile properties
        assertNotNull("Projectile should be created", foundProjectile);
        
        // Projectile should be positioned relative to the enemy
        assertTrue("Projectile X position should be near the enemy",
            Math.abs(foundProjectile.getX() - (x + enemy.getWidth()/2 - Utilities.PROJECTILE_SIZE/2)) < 1.0);
            
        assertEquals(y + enemy.getHeight() - Utilities.PROJECTILE_SIZE/2, foundProjectile.getY(), 1.0);
        assertEquals(0.0, foundProjectile.xVelocity, 0.001);
        assertEquals(10.0, foundProjectile.yVelocity, 0.001);
        assertFalse("Enemy projectile should not be friendly", foundProjectile.isFriendly());
    }
    
}