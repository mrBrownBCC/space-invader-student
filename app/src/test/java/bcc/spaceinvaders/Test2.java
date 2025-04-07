package bcc.spaceinvaders;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Image;

public class Test2 {

    private GamePanel gamePanel;

    @Before
    public void setUp() {
        System.out.println("Setting up test environment...");
        gamePanel = new GamePanel(true);
        Utilities.setTestMode(true);
        Utilities.resetKeyStates();
    }

    @After
    public void tearDown() {
        Utilities.setTestMode(false);
    }

    // Test 1: Verify Ship constructor initializes values correctly
    @Test
    public void testShipConstructor() {
        double x = 100;
        double y = 200;
        int width = 50;
        int height = 60;
        Image image = Utilities.PLAYER_NORMAL_IMAGE;
        int health = 75;

        Ship ship = new Ship(x, y, width, height, image, health, gamePanel);

        assertEquals(x, ship.getX(), 0.001);
        assertEquals(y, ship.getY(), 0.001);
        assertEquals(width, ship.getWidth(), .01);
        assertEquals(height, ship.getHeight(), .01);
        assertEquals(health, ship.health);
        assertEquals(health, ship.maxHealth);
    }

    // Test 2: Verify PlayerShip constructor initializes values correctly
    @Test
    public void testPlayerShipConstructor() {
        double x = 150;
        double y = 250;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);

        assertEquals(x, playerShip.getX(), 0.001);
        assertEquals(y, playerShip.getY(), 0.001);
        assertEquals(70, playerShip.getWidth(), .01);
        assertEquals(70, playerShip.getHeight(), .01);
        assertEquals(100, playerShip.health);
        assertEquals(100, playerShip.maxHealth);
    }

    // Test 3: Verify PlayerShip doesn't move when no keys are pressed
    @Test
    public void testPlayerShipNoMovement() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        playerShip.update();

        assertEquals(x, playerShip.getX(), 0.001);
        assertEquals(y, playerShip.getY(), 0.001);
        assertEquals(0, playerShip.xVelocity, 0.001);
        assertEquals(0, playerShip.yVelocity, 0.001);
    }

    // Test 4: Verify PlayerShip moves up when W key is pressed
    @Test
    public void testPlayerShipMoveUp() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        Utilities.setKeyPressed(KeyEvent.VK_W, true);

        playerShip.update();

        assertEquals(x, playerShip.getX(), 0.001);
        assertTrue(playerShip.getY() < y); // Check it moved up
        assertEquals(0, playerShip.xVelocity, 0.001);
        assertEquals(-Utilities.PLAYER_SPEED, playerShip.yVelocity, 0.001);
    }

    // Test 5: Verify PlayerShip moves down when S key is pressed
    @Test
    public void testPlayerShipMoveDown() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        Utilities.setKeyPressed(KeyEvent.VK_S, true);

        playerShip.update();

        assertEquals(x, playerShip.getX(), 0.001);
        assertTrue(playerShip.getY() > y); // Check it moved down
        assertEquals(0, playerShip.xVelocity, 0.001);
        assertEquals(Utilities.PLAYER_SPEED, playerShip.yVelocity, 0.001);
    }

    // Test 6: Verify PlayerShip moves left when A key is pressed
    @Test
    public void testPlayerShipMoveLeft() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        Utilities.setKeyPressed(KeyEvent.VK_A, true);

        playerShip.update();

        assertTrue(playerShip.getX() < x); // Check it moved left
        assertEquals(y, playerShip.getY(), 0.001);
        assertEquals(-Utilities.PLAYER_SPEED, playerShip.xVelocity, 0.001);
        assertEquals(0, playerShip.yVelocity, 0.001);
    }

    // Test 7: Verify PlayerShip moves right when D key is pressed
    @Test
    public void testPlayerShipMoveRight() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        Utilities.setKeyPressed(KeyEvent.VK_D, true);

        playerShip.update();

        assertTrue(playerShip.getX() > x); // Check it moved right
        assertEquals(y, playerShip.getY(), 0.001);
        assertEquals(Utilities.PLAYER_SPEED, playerShip.xVelocity, 0.001);
        assertEquals(0, playerShip.yVelocity, 0.001);
    }

    // Test 8: Verify PlayerShip diagonal movement when multiple keys are pressed
    @Test
    public void testPlayerShipDiagonalMovement() {
        double x = 200;
        double y = 300;

        PlayerShip playerShip = new PlayerShip(x, y, gamePanel);
        Utilities.setKeyPressed(KeyEvent.VK_W, true);
        Utilities.setKeyPressed(KeyEvent.VK_D, true);

        playerShip.update();

        assertTrue(playerShip.getX() > x); // Check it moved right
        assertTrue(playerShip.getY() < y); // Check it moved up
        assertEquals(Utilities.PLAYER_SPEED, playerShip.xVelocity, 0.001);
        assertEquals(-Utilities.PLAYER_SPEED, playerShip.yVelocity, 0.001);
    }

    // Test 9: Verify keepInBounds corrects position when ship is outside left
    // boundary
    @Test
    public void testPlayerShipKeepInBoundsLeft() {
        PlayerShip playerShip = new PlayerShip(-10, 200, gamePanel);
        playerShip.keepInBounds();

        assertEquals(0, playerShip.getX(), 0.001);
        assertEquals(200, playerShip.getY(), 0.001);
    }

    // Test 10: Verify keepInBounds corrects position when ship is outside right
    // boundary
    @Test
    public void testPlayerShipKeepInBoundsRight() {
        int screenWidth = Utilities.SCREEN_WIDTH;
        PlayerShip playerShip = new PlayerShip(screenWidth + 10, 200, gamePanel);
        playerShip.keepInBounds();

        assertEquals(screenWidth - playerShip.getWidth(), playerShip.getX(), 0.001);
        assertEquals(200, playerShip.getY(), 0.001);
    }

    // Test 11: Verify keepInBounds corrects position when ship is outside top
    // boundary
    @Test
    public void testPlayerShipKeepInBoundsTop() {
        PlayerShip playerShip = new PlayerShip(200, -10, gamePanel);
        playerShip.keepInBounds();

        assertEquals(200, playerShip.getX(), 0.001);
        assertEquals(0, playerShip.getY(), 0.001);
    }

    // Test 12: Verify keepInBounds corrects position when ship is outside bottom
    // boundary
    @Test
    public void testPlayerShipKeepInBoundsBottom() {
        int screenHeight = Utilities.SCREEN_HEIGHT;
        PlayerShip playerShip = new PlayerShip(200, screenHeight + 10, gamePanel);
        playerShip.keepInBounds();

        assertEquals(200, playerShip.getX(), 0.001);
        assertEquals(screenHeight - playerShip.getHeight(), playerShip.getY(), 0.001);
    }

    @Test
    public void testPlayerShipRotationDisplay() {
        PlayerShip playerShip = new PlayerShip(200, 300, gamePanel);

        // Create a temporary image and graphics object for testing
        java.awt.image.BufferedImage tempImage = new java.awt.image.BufferedImage(
                800, 600, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics tempGraphics = tempImage.getGraphics();

        // Test rotation when moving left
        playerShip.xVelocity = -5;
        playerShip.display(tempGraphics); // Use the temp graphics object
        assertTrue(playerShip.rotation < 0);

        // Test rotation when moving right
        playerShip.xVelocity = 5;
        playerShip.display(tempGraphics); // Use the temp graphics object
        assertTrue(playerShip.rotation > 0);

        // Test rotation when not moving horizontally
        playerShip.xVelocity = 0;
        playerShip.display(tempGraphics); // Use the temp graphics object
        assertEquals(0, playerShip.rotation, 0.001);

        // Clean up
        tempGraphics.dispose();
    }

    // Test 14: Verify update method calls keepInBounds
    @Test
    public void testUpdateCallsKeepInBounds() {
        PlayerShip playerShip = new PlayerShip(-10, -10, gamePanel);
        playerShip.update();

        // If keepInBounds is called, the ship should be moved back inside the screen
        assertEquals(0, playerShip.getX(), 0.001);
        assertEquals(0, playerShip.getY(), 0.001);
    }
}