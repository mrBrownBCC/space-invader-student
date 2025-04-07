package bcc.spaceinvaders;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3 {
    
    private GamePanel gamePanel;
    
    @Before
    public void setUp() {
        System.out.println("Setting up star test environment...");
        gamePanel = new GamePanel(true);
    }
    
    // Test 1: Verify Star constructor initializes random values within expected ranges
    @Test
    public void testStarConstructor() {
        for (int i = 0; i < 100; i++) { // Test multiple stars to ensure random values work properly
            Star star = new Star();
            
            // Verify x position is within screen bounds
            assertTrue("X position should be within screen bounds",
                     star.getX() >= 0 && star.getX() <= Utilities.SCREEN_WIDTH);
            
            // Verify y position is within screen bounds
            assertTrue("Y position should be within screen bounds",
                     star.getY() >= 0 && star.getY() <= Utilities.SCREEN_HEIGHT);
            
            // Verify velocity is within expected range (1-3)
            assertTrue("Y velocity should be >0",
                     star.getYVelocity() > 0);
            // Verify brightness is between 0 and 1
            assertTrue("Brightness should be between 0 and 1",
                     star.getBrightness() >= 0 && star.getBrightness() <= 1);
        }
    }
    
    // Test 2: Verify initializeStars creates enough stars (more than 50)
    @Test
    public void testInitializeStars() {
        // We can now use the getStars() method to access the stars array
        Star[] stars = gamePanel.getStars();
        
        // Verify stars array exists and has more than 50 elements
        assertNotNull("Stars array should not be null", stars);
        assertTrue("Should have at least 50 stars", stars.length > 50);
        
        // Verify each star is properly initialized
        for (Star star : stars) {
            assertNotNull("Each star should be initialized", star);
            
            // Additional checks on star properties
            assertTrue("Star X position should be within screen bounds", 
                    star.getX() >= 0 && star.getX() <= Utilities.SCREEN_WIDTH);
            assertTrue("Star Y position should be within screen bounds", 
                    star.getY() >= 0 && star.getY() <= Utilities.SCREEN_HEIGHT);
        }
    }
    
    // Test 3: Verify Star update method moves stars down and resets them properly
    @Test
    public void testStarUpdate() {
        Star star = new Star();
        
        // Store original position
        double originalX = star.getX();
        double originalY = star.getY();
        double velocity = star.getYVelocity();
        
        // Update the star
        star.update();
        
        // Verify the star moved down by its velocity
        assertEquals("Star should move down by its velocity", 
                 originalY + velocity, star.getY(), 0.001);
        
        // Verify x position didn't change
        assertEquals("X position should not change during normal update", 
                 originalX, star.getX(), 0.001);
        
        // Test star reset when it goes off screen
        star = new Star();
        
        // Put star at bottom edge
        star.setY(Utilities.SCREEN_HEIGHT + 1);
        star.update();
        
        // Verify star was reset to top
        assertEquals("Star should reset to top of screen", 0.0, star.getY(), 0.001);
    }
    
}