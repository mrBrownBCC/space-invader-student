package bcc.spaceinvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private PlayerShip player;
    private ArrayList<GameObject> gameObjects;
    private Star[] stars;
    private int curLevel = 0;
    private int messageTimer = 0;
    private String message = "";
    private int score = 0;
    private boolean gameOver = false;

    public GamePanel(boolean testMode) {
        setPreferredSize(new Dimension(Utilities.SCREEN_WIDTH, Utilities.SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        System.out.println("GamePanel constructor - TEST MODE: " + testMode);
        if(!testMode)Utilities.loadImages();
        initializeStars();
        gameObjects = new ArrayList<GameObject>();

        player = new PlayerShip(400, 300, this);
        gameObjects.add(player);

        timer = new Timer(32, this); // ~30 FPS
        timer.start();
        beginLevel(0);
    }

    public void initializeStars() {
        // step 3.2 - create an array of stars and use a for loop to initialize them. Note that the 'stars' variable is already declared for you above. 
        // The size of the array should be > 50 to pass tests but is up to you!
        stars = new Star[100];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Utilities.SCREEN_WIDTH, Utilities.SCREEN_HEIGHT);
        for (Star star : stars) {
            star.display(g);
        }
        for (GameObject obj : gameObjects) {
            obj.display(g);
        }
        //display score in top left corner
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 30);

        if(messageTimer > 0){
            messageTimer--;
            g.setColor(Color.WHITE);
    
            // Create font and get metrics for text centering
            Font messageFont = new Font("Arial", Font.BOLD, 24);
            g.setFont(messageFont);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(message);
            int textHeight = fm.getHeight();
            
            // Calculate ellipse dimensions
            int ellipseWidth = textWidth + 60;
            int ellipseHeight = textHeight + 30;
            int ellipseX = (Utilities.SCREEN_WIDTH - ellipseWidth) / 2;
            int ellipseY = (Utilities.SCREEN_HEIGHT - ellipseHeight) / 2;
            
            // Draw the ellipse
            g.fillOval(ellipseX, ellipseY, ellipseWidth, ellipseHeight);
            
            // Draw the text in black
            g.setColor(Color.BLACK);
            int textX = (Utilities.SCREEN_WIDTH - textWidth) / 2;
            int textY = (Utilities.SCREEN_HEIGHT + textHeight / 2) / 2;
            g.drawString(message, textX, textY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {// think of this as the game loop
        if(gameOver){
            return;
        }

        for (Star star : stars) {
            star.update();
        }

        for (int i = 0; i < gameObjects.size(); i++) {// using standard for loop to avoid concurrent modification
                                                      // exception
            GameObject obj = gameObjects.get(i);
            obj.update();
        }

        // hanle any collisions
        // Handle any collisions using indexed loops to avoid double counting
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject obj1 = gameObjects.get(i);
                GameObject obj2 = gameObjects.get(j);

                if (obj1.checkCollision(obj2)) {
                    obj1.handleCollision(obj2);
                    obj2.handleCollision(obj1);
                }
            }
        }

        //handle deaths
        for(int i = gameObjects.size() - 1; i >= 0; i--) {
            GameObject obj = gameObjects.get(i);
            if (!obj.isAlive()) {
                if(obj instanceof EnemyShip) {

                   score += ((EnemyShip)obj).getPointValue();
                }
                gameObjects.remove(i);
            }
        }

        if (completedLevel()) {
            curLevel++;
            beginLevel(curLevel);
        }

        if(!player.isAlive()) {
            //game over
            gameOver = true;
            message = "Game Over. Final Score: " + score;
            messageTimer = 1000000;
        }
        repaint();
    }

    public boolean completedLevel() {
        //step 5.8 - check if the level is completed. The level is completed if there are no enemy ships in the gameObjects ArrayList.
        //use instanceof!
        //return true if the level is completed and false otherwise.
        for (GameObject obj : gameObjects) {
            if (obj instanceof EnemyShip) {
                return false; // If any enemy ship is present, level is not completed
            }
        }
        return true; // No enemy ships present, level is completed
    }

    public void addProjectile(Projectile p) {
        gameObjects.add(p);
    }

    public void beginLevel(int level) {
        messageTimer = 100;
        message = "Level " + (level);
        if (level == 0) {
            //step 4.3 - Add enemies for level 0. You should add 3 SpeedyEnemies with random x positions and y = -100.
            //NOTE - the last parameeter should be 'this'
            //after you make each enemy, add it to the gameObjects ArrayList
            for (int i = 0; i < 3; i++) {
                double x = Math.random() * Utilities.SCREEN_WIDTH;
                double y = - 100;
                EnemyShip enemy = new SpeedyEnemy(x, y, this);
                gameObjects.add(enemy);
            }
        } else if (level == 1) {
            //step 6.3- Add enemies for level 1. You should add 3 TankyEnemies with x positions of 250, 350, and 450 and y = -150.
            //try to use a for loop to do this.
            //after you make each enemy, add it to the gameObjects ArrayList
            for(int i = 0; i < 3; i ++){
                gameObjects.add(new TankyEnemy(250 + 100*i, -150,this));
            }
        } else if(level == 2){
            //step 6.5- Add enemies for level 2. You should add 1 Boss with x position of 300 and y = -200.
            gameObjects.add(new Boss(Utilities.SCREEN_WIDTH / 2 - 100, -200, this));
        } else if(level == 3){
            
        } else if(level == 4){
            
        } else {
            gameOver = true;
            message = "You Win! Score: " + score;
            messageTimer = 1000000;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Utilities.handleKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Utilities.handleKeyReleased(e.getKeyCode());

        // Spacebar shoots
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.shoot();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public PlayerShip getPlayer() {
        return player;
    }

    public Star[] getStars() {
        return stars;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}
