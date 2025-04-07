package bcc.spaceinvaders;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class Utilities {
    public static BufferedImage PLAYER_NORMAL_IMAGE;
    public static BufferedImage PLAYER_DAMAGE_IMAGE;
    public static BufferedImage ENEMY_IMAGE;
    public static BufferedImage PROJECTILE_IMAGE;
    public static BufferedImage SPEEDY_ENEMY_IMAGE;
    public static BufferedImage TANK_ENEMY_IMAGE;
    public static BufferedImage BOSS_IMAGE;
    public static BufferedImage BOSS_PROJECTILE_IMAGE;
    public static BufferedImage ENEMY_PROJECTILE_1_IMAGE;
    public static BufferedImage ENEMY_PROJECTILE_2_IMAGE;
    public static BufferedImage ENEMY_PROJECTILE_3_IMAGE;
    public static BufferedImage PLAYER_PROJECTILE_IMAGE;


    public static double PLAYER_SPEED = 7;
    public static final int PROJECTILE_SIZE = 25;

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static ArrayList<Integer> keysPressed = new ArrayList<Integer>();

    public static void loadImages() {
        try {
            PLAYER_NORMAL_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/playerNormal.png")));
            PLAYER_DAMAGE_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/playerDamage.png")));
            SPEEDY_ENEMY_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/speedyEnemy.png")));
            TANK_ENEMY_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/tankEnemy.png")));
            BOSS_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/bossEnemy.png")));
            ENEMY_PROJECTILE_1_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/enemyBullet1.png")));
            ENEMY_PROJECTILE_2_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/enemyBullet2.png")));
            BOSS_PROJECTILE_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/bossProjectileImage.png")));
            PLAYER_PROJECTILE_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/userProjectile.png")));
            ENEMY_PROJECTILE_3_IMAGE = cropToContent(ImageIO.read(new File("app/src/main/resources/images/enemyBullet3.png")));
    
        } catch (IOException e) {
            System.err.println("Failed to load one or more images: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static BufferedImage cropToContent(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
    
        int minX = width;
        int minY = height;
        int maxX = 0;
        int maxY = 0;
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = src.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
    
                if (alpha > 0) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
    
        if (maxX < minX || maxY < minY) {
            // No visible content
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    
        return src.getSubimage(minX, minY, (maxX - minX + 1), (maxY - minY + 1));
    }

    public static void handleKeyPressed(int keyCode) {
        if (!keysPressed.contains(keyCode)) {
            keysPressed.add(keyCode);
        }
    }
    
    public static void handleKeyReleased(int keyCode) {
        for (int i = 0; i < keysPressed.size(); i++) {
            if (keysPressed.get(i) == keyCode) {
                keysPressed.remove(i); // Remove using index for clarity
                break;
            }
        }
    }
    
    // For testing purposes only
private static boolean[] testKeyStates = new boolean[256];

// Test helper methods
public static void resetKeyStates() {
    for (int i = 0; i < testKeyStates.length; i++) {
        testKeyStates[i] = false;
    }
}

public static void setKeyPressed(int keyCode, boolean isPressed) {
    if (keyCode < testKeyStates.length) {
        testKeyStates[keyCode] = isPressed;
    }
}

// Modify the existing isKeyPressed method to use the test states in test mode
private static boolean inTestMode = false;

public static void setTestMode(boolean enabled) {
    inTestMode = enabled;
}

// Update the existing isKeyPressed method
public static boolean isKeyPressed(int keyCode) {
    if (inTestMode) {
        return keyCode < testKeyStates.length && testKeyStates[keyCode];
    } else {
        // Original implementation
        return keysPressed.contains(keyCode);
    }
}
    


}
