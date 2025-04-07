package bcc.spaceinvaders;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Test6 {
    
    private GamePanel gamePanel;
    private BufferedImage tempImage;
    private Graphics tempGraphics;
    
    @Before
    public void setUp() {
        System.out.println("Setting up test environment for TankyEnemy and Boss...");
        gamePanel = new GamePanel(true);
        
        // Create mock graphics objects for display methods
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
    
    // Test 1: Verify TankyEnemy constructor initializes values correctly
    @Test
    public void testTankyEnemyConstructor() {
        double x = 150;
        double y = 250;
        
        TankyEnemy enemy = new TankyEnemy(x, y, gamePanel);
        
        // Test positioning
        assertEquals(x, enemy.getX(), 0.001);
        assertEquals(y, enemy.getY(), 0.001);
        
        // Test dimensions
        assertEquals(100, enemy.getWidth(), 0.01);
        assertEquals(100, enemy.getHeight(), 0.01);
        
        // Test velocities
        assertEquals(2.0, enemy.getXVelocity(), 0.001);
        assertEquals(0.5, enemy.getYVelocity(), 0.001);
        
        // Test health and point value
        assertEquals(30, enemy.getHealth());
        assertEquals(20, enemy.getPointValue());
        
        // Test rotation
        assertEquals(180, enemy.getRotation(), 0.001);
    }
    
    // Test 2: Verify TankyEnemy update method changes direction at the right time
    @Test
    public void testTankyEnemyUpdate() {
        TankyEnemy enemy = new TankyEnemy(200, 200, gamePanel);
        double initialXVelocity = enemy.getXVelocity();
        
        // Use getters for dirSwitchTimer and dirSwitchInterval
        int dirSwitchInterval = enemy.getDirSwitchInterval();
        
        // Set timer to just before interval
        enemy.setDirSwitchTimer(dirSwitchInterval - 1);
        
        // Update once - should not change direction yet
        enemy.update();
        assertEquals(initialXVelocity, enemy.getXVelocity(), 0.001);
        
        // Set timer to exactly at interval
        enemy.setDirSwitchTimer(dirSwitchInterval);
        
        // Update again - should change direction this time
        enemy.update();
        assertEquals(-initialXVelocity, enemy.getXVelocity(), 0.001);
        
        // Check timer reset
        assertEquals(0, enemy.getDirSwitchTimer());
    }
    
    // Test 3: Verify GamePanel.beginLevel(1) creates TankyEnemies
    @Test
    public void testBeginLevelCreatesTankyEnemies() {
        gamePanel.getGameObjects().clear();
        
        // Start level 1
        gamePanel.beginLevel(1);
        
        // Count TankyEnemy instances
        int tankyEnemyCount = 0;
        for (GameObject obj : gamePanel.getGameObjects()) {
            if (obj instanceof TankyEnemy) {
                tankyEnemyCount++;
            }
        }
        
        assertEquals("Level 1 should create 3 TankyEnemy instances", 3, tankyEnemyCount);
        
        // Verify positions of TankyEnemies (they should be at x=250, 350, 450)
        boolean found250 = false;
        boolean found350 = false;
        boolean found450 = false;
        
        for (GameObject obj : gamePanel.getGameObjects()) {
            if (obj instanceof TankyEnemy) {
                double xPos = obj.getX();
                if (Math.abs(xPos - 250) < 0.1) found250 = true;
                if (Math.abs(xPos - 350) < 0.1) found350 = true;
                if (Math.abs(xPos - 450) < 0.1) found450 = true;
            }
        }
        
        assertTrue("Should have TankyEnemy at x=250", found250);
        assertTrue("Should have TankyEnemy at x=350", found350);
        assertTrue("Should have TankyEnemy at x=450", found450);
    }
    
    // Test 4: Verify Boss constructor initializes values correctly
    @Test
    public void testBossConstructor() {
        double x = 300;
        double y = 100;
        
        Boss boss = new Boss(x, y, gamePanel);
        
        // Test positioning
        assertEquals(x, boss.getX(), 0.001);
        assertEquals(y, boss.getY(), 0.001);
        
        // Test dimensions
        assertEquals(200, boss.getWidth(), 0.01);
        assertEquals(200, boss.getHeight(), 0.01);
        
        // Test velocities
        assertEquals(4.0, boss.getXVelocity(), 0.001);
        assertEquals(1.0, boss.getYVelocity(), 0.001);
        
        // Test health and point value
        assertEquals(100, boss.getHealth());
        assertEquals(100, boss.getPointValue());
    }
    
    // Test 5: Verify GamePanel.beginLevel(2) creates a Boss
    @Test
    public void testBeginLevelCreatesBoss() {
        gamePanel.getGameObjects().clear();
        
        // Start level 2
        gamePanel.beginLevel(2);
        
        // Count Boss instances
        int bossCount = 0;
        Boss foundBoss = null;
        
        for (GameObject obj : gamePanel.getGameObjects()) {
            if (obj instanceof Boss) {
                bossCount++;
                foundBoss = (Boss) obj;
            }
        }
        
        assertEquals("Level 2 should create 1 Boss instance", 1, bossCount);
        
        // Verify Boss is positioned correctly (should be near center of screen)
        assertNotNull("Boss should not be null", foundBoss);
        assertEquals(Utilities.SCREEN_WIDTH / 2 - 100, foundBoss.getX(), 0.1);
        assertEquals(-200, foundBoss.getY(), 0.1);
    }
    
    // Test 6: Verify Boss update method stops vertical movement
    @Test
    public void testBossUpdate() {
        Boss boss = new Boss(300, -50, gamePanel);
        
        // Initially yVelocity should be 1
        assertEquals(1.0, boss.getYVelocity(), 0.001);
        
        // Update once - should still be moving down
        boss.update();
        
        // Check that y position increased
        assertTrue(boss.getY() > -50);
        
        // Set y position to positive (above screen)
        boss.setY(50);
        
        // Update again - should stop moving down
        boss.update();
        
        // Check that yVelocity is now 0
        assertEquals(0.0, boss.getYVelocity(), 0.001);
        
        // But xVelocity should still be non-zero (still moving horizontally)
        assertTrue(Math.abs(boss.getXVelocity()) > 0);
    }
    
    // Test 7: Verify Boss shoots GreenProjectile instead of regular projectile
    @Test
    public void testBossShootsGreenProjectile() {
        Boss boss = new Boss(300, 100, gamePanel);
        gamePanel.getGameObjects().clear();
        
        // Make boss shoot
        boss.shoot();
        
        // Check if a GreenProjectile was added
        boolean foundGreenProjectile = false;
        
        for (GameObject obj : gamePanel.getGameObjects()) {
            if (obj instanceof GreenProjectile) {
                foundGreenProjectile = true;
                break;
            }
        }
        
        assertTrue("Boss should shoot a GreenProjectile", foundGreenProjectile);
    }
    
    // Test 8: Verify GreenProjectile follows player
    @Test
    public void testGreenProjectileFollowsPlayer() {
        // Create player and add to game
        PlayerShip player = new PlayerShip(400, 400, gamePanel);
        gamePanel.getGameObjects().clear();
        gamePanel.getGameObjects().add(player);
        
        // Create a GreenProjectile
        GreenProjectile projectile = new GreenProjectile(200, 200, gamePanel);
        
        // Initially velocities should be zero
        assertEquals(0.0, projectile.getXVelocity(), 0.001);
        
        // Update the projectile - it should start tracking the player
        projectile.update();
        
        // Check that velocities were set to move toward player
        // (Player is to the right and below, so both velocities should be positive)
        assertTrue("X velocity should point toward player", projectile.getXVelocity() > 0);
        
        // Now move the player to a new position (left and above the projectile)
        gamePanel.getPlayer().setX(0);
        
        // Update the projectile again
        projectile.update();
        projectile.update();
        projectile.update();
        projectile.update();
        projectile.update();
    
        // Velocities should now be negative to track the new player position
        assertTrue("X velocity should adjust to new player position", projectile.getXVelocity() < 0);
        
        // Test handling of null player (shouldn't crash)
        gamePanel.getGameObjects().clear(); // Remove player
        
    }
}