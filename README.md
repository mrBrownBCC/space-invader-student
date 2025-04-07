# Setup Instructions
In order to run the GUI:
Go to the ports tab. Click "Add port" and type the number 6080.
Follow the link to your forwarded address. This is where your GUI should be available once you run your code.
If you can't connect to noVNC, try setting the connection to public in the port 

To access the code, go to app > src > main > java

# Example
This is a new thing! Try out the finished version of the project by running:
```
java -jar MR_BROWN_EXAMPLE.jar
```
And following the link to port 6080

# Programming Instructions

General notes (same as from battleship lab):
1. Don't change any method headers. These will be correct and may be used by the automated tests. 
2. For testing run the given command in the terminal. It will be of the pattern gradle ______
3. Say hello to your (maybe) first real code editor (if you used codehs for the last project). Autocomplete, error highlighting, and other things are about to make your life so much easier. 
4. Apply for the student github developer pack!!
5. For each step, more detail may be given in code comments
6. As a new recommendation - Commit after each section is complete, in case you make a catostrophic mistake that breaks everything, you can go back to this point in time. 
7. Commands should be run in the terminal at the bottom of the screen. To kill the program running, use CTRL - C. To get the terminal to appear, simply drag up from the bottom. 
8. To run the code, navigate to App.java and click the run button in the top right. 

Note - you may notice that the below percentages sum to 50. 50% comes from just turning in the project! There is a test called test1 that contains this freebie 50%. 

## Part 1 - Exploration
As a class, we will take a look at the starter code for each class. Explore more on your own!

To get in the habit, run 
```
gradle test1
```

## Part 2 - Player Ship Movement - 10%

1. First things first, we need to be able to make a ship class in the first place. Complete the constructor in Ship.java.
2. PlayerShip extends Ship. Complete the PlayerShip Constructor
3. Write the player movement portion of the PlayerShip update method. Then test this out in the GUI!
4. Write the keepInBounds method in the PlayerShip class. 
5. Complete the display method in the PlayerShip class. 

RUN THE TESTS(before doing this, it is a better idea to run your GUI and see if the change you make has the intended consequence. ). To earn the points for this section, you must pass all of the tests. 
```
gradle test2
```

## Part 3 - Background Stars - 5%
This part of the code is straightforward enough, but simple code can make such a big difference in the feel of the game!
Test the new GUI out after steps 2 and 3, choose values that you like for the size, speed, and number of stars. 

1. Complete the Star Constructor
2. Complete the intializeStars method in GamePanel.java
3. Complete the Star update method

RUN THE TESTS(before doing this, it is a better idea to run your GUI and see if the change you make has the intended consequence. ). To earn the points for this section, you must pass all of the tests. 
```
gradle test3
```

## Part 4 - Speedy Enemy + Projectiles - 10%
In the interest of time, I am leaving most starter code for the EnemyShip Class. This code currently calls the shoot method every maxCooldown frames. 
It also keeps the ship in bounds from left to right, bouncing it away from the wall. If the ship goes below the bottom of the screen, it will loop back to the top. 

1. Write the constructor for SpeedyEnemy
2. Write the update method for SpeedyEnemy
3. Let's make some! Add these ships in the beginLevel method in GamePanel.java. At this point when you test, the ships should move around and down the screen, but not interact with anything. 
4. Write the Projectile Constructor in Projectile.java
5. Write the Shoot method in EnemyShip.java

Go ahead and test! At this point, the enemy ships should shoot, but the bullets won't do anything. 
RUN THE TESTS(before doing this, it is a better idea to run your GUI and see if the change you make has the intended consequence. ). To earn the points for this section, you must pass all of the tests. 
```
gradle test4
```

## Part 5 - Damage, level progression, player shooting, and death - 15%
Now we need projectiles to actually do something when they collide with a ship. Let's do it! The following code is already given in GamePanel.java that loops over objects and calls the handleCollision method to run your code when collisions happen. I have posted this below:
```
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
```

Also in GamePanel.java is code to remove objects whose isAlive method returns false. This will be important for steps 3+. Points are also automatically given for killed enemies. 
```
for(int i = gameObjects.size() - 1; i >= 0; i--) {
    GameObject obj = gameObjects.get(i);
    if (!obj.isAlive()) {
        if(obj instanceof EnemyShip) {
           score += ((EnemyShip)obj).getPointValue();
        }
        gameObjects.remove(i);
    }
}
```

1. We need to implement collision detection. Complete the checkCollision method in GameObject.java. 
2. The player should take damage when hit by NOT friendly projectiles. Implement this in PlayerShip's handleCollision method. 
3. Now the player should take damage, but the health can actually go negative without the player dying. To fix this, we need to implement the isAlive method in the ship class. 
4. Now, let's let the player ship fire back! Complete the shoot method in the PlayerShip Class. 
5. EnemyShips should take damage when hit by friendly projectiles. Write the handleCollision method for EnemyShip. 
6. Now projectiles are out in the world doing damage, but they don't ever go away. Complete the handleCollision method in Projectile.java
7. To let these projectiles die, complete the isAlive method for Projectiles.java. 
8. Now that we have code to remove ships, we need to move to the next level if we have beat all enemies. To do this, complete the completedLevel method in GamePanel.java. 

RUN THE TESTS(before doing this, it is a better idea to run your GUI and see if the change you make has the intended consequence. ). To earn the points for this section, you must pass all of the tests. 
```
gradle test5
```

## Part 6 - TankyEnemy and Boss Enemy - 10
Now that we have the basic mechanics down, inheritance makes it very easy to add new enemy types. 

1. TankyShip.java constructor. 
2. TankyShip.java update method. 
3. Make level 1 have TankyShips! Complete in GamePanel.java. 
Wasn't that easy!
4. Let's make the final boss enemy. Complete Boss.java Constructor. 
5. Make level 2 contain the boss for further testing. Add this in GamePanel.java. 
6. Complete the update method in Boss.java. 
7. The boss class shoots a different projectile! I have left most of the starter code in for this. This projectile has a special behavior - it follows the player. Implement this behavior in the update method of GreenProjectile.java.  


RUN THE TESTS(before doing this, it is a better idea to run your GUI and see if the change you make has the intended consequence. ). To earn the points for this section, you must pass all of the tests. 
```
gradle test6
```


## BONUS
In the future, if there is interest, we will revisit this project and add cool extensions. Some ideas - 
1. Add custom images
2. Add cool background stuff
3. Add new enemies (NOTE THAT TO PASS TESTS AND EXPERIMENT WITH NEW ENEMIES EASILY, YOU CAN JUST SET curLevel TO BE 3 AND ADD ALL YOUR COOL NEW STUFF THERE)
4. Fancy new projectiles with special effects (freezing, heat seaking, explosive, etc)
5. Power ups
6. add cool animations
7. A currency + upgrades

# Submission
1. Testing everything. Run the command - 
``` 
gradle test
```
2. Submit on github classroom by running the following commands. This also saves your work permanently. 

```
gradle clean build
git add . 
git commit -m "submitting"
git push
```
