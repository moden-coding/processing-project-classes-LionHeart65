import java.util.ArrayList;

import Interfaces.Item;
import processing.core.PApplet;
// commenting
public class App extends PApplet {

    static Player player;
    static int charX = 0;
    static int charY = 0;
    int lr = 1; // is char facing left or right, 1 is right, -1 is left
    int ud = 1; // is char facing up or down, 1 is up, -1 is down
    float speed = 1; // speed of the player, adjusted to move diagonally
    float speedStat = 3; // raw speed of the player
    int bg = color(46, 89, 61);
    int gameCode = 0; // what scene is the game on, 0 is menu, 1 is instructions, 2 is game
    static int hp = 100;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    boolean moveXPos = false; // is the player moving in these directions
    boolean moveXNeg = false;
    boolean moveYPos = false;
    boolean moveYNeg = false;
    int iFrames = 0; // frames since player lost a life.
    boolean lostLife = false; // has the player lost a life within 3 secs
    static boolean swung;
    int swingTime = 15;
    int swingFrames = 0;
    static ArrayList<Obstacle> obstacles = new ArrayList<>();
    int saveTime = 1800;
    static int highScore = 0;

    boolean day = true;
    int dayTime = 300; //how many frames long a day is
    int nightTime = 750; //how many frames long a night is
    int timeCounter = 0; // current time since beginning of day/night
    static int dayNum = 0;
    int daySpawns = 1; // how many spawns per day
    int spawnTimer = 1; // the time between each spawn

    public static void swing() {
        swung = true;
    }

    public static boolean getSwung() {
        return swung;
    }

    public static int getHP() {
        return hp;
    }

    public static void setHP(int health) {
        hp = health;
    }

    public void settings() { // Sets the background size
        size(1500, 1000);
    }

    public void setup() {
        charX = width / 2; // sets player starting coords.
        charY = height / 2;
        background(bg);
        player = new Player(width / 2, height / 2, this);
        Loading.load(this);
    }

    public static void setObstacles(ArrayList<Obstacle> newObs) {
        obstacles = newObs;
    }

    // following methods get random (X, Y) values to spawn Enemies in random
    // locations.
    public int[] randCoord() {
        int[] coords = new int[2];
        coords[0] = Math.round(random(50, width - 50)); // Ensure within visible bounds
        coords[1] = Math.round(random(50, height - 50)); // Ensure within visible bounds
        return coords;
    }

    public static void setCoords(int X, int Y) {
        player.setCoords(X, Y);
    }

    public void draw() {
        // decides which scene to show based on game code value.
        switch (gameCode) {
            case 0:
                menu();
                break;
            case 1:
                instructions();
                break;
            case 2:
                play();
                break;
        }
    }

    public static void syncCoords(int X, int Y) {
        charX = X;
        charY = Y;
    }

    // main game function, where the playing happens. 1
    public void play() {
        //handels stuff specific for day or night, sets time, color, makes enemies spawn at night
        if (day) {
            timeCounter++;
            if (timeCounter >= dayTime) {
                day = false;
                bg = color(27, 54, 36);
                timeCounter = 0;
                if (dayNum % 3 == 0) {
                    // makes the spawns go up every three days
                    daySpawns += Math.ceil(dayNum / 5);
                    spawnTimer = nightTime / daySpawns;
                }
                if (dayNum % 10 == 0) {
                    // adds new obstacles every 10 days
                    for (int i = 0; i < 10; i++) {
                        obstacles.add(new Rock(randCoord()[0], randCoord()[1], this, 5));
                    }
                }
                dayNum++;

            }
        } else { // if night
            //adds enemies throughout the night at even intervals.
            if (timeCounter == 0) {
                Enemies.add(new Enemy(randCoord()[0], randCoord()[1], this));
            }
            timeCounter++;

            if (timeCounter >= nightTime) {
                //switches back to day
                day = true;
                bg = color(46, 89, 61);
                timeCounter = 0;
            }
            if (timeCounter % spawnTimer == 0) {
                Enemies.add(new Enemy(randCoord()[0], randCoord()[1], this));
            }

        }

        background(bg);
        if (frameCount % saveTime == 0) {
            //save game every saveTime frames
            Loading.save(Player.getInv(), obstacles, charX, charY);
        }
        if (hp <= 0) {
            // if die, reset game
            Loading.reset(this);
            gameCode = 0;


        }
        if (swung) {
            // swing cooldown, keeps it swung for a bit
            swingFrames++;
            if (swingFrames >= swingTime) {
                swingFrames = 0;
                swung = false;
            }
        }

        fill(255);
        textSize(32);
        text("Day: " + dayNum, 96, 50);
        text("HP: " + hp, 100, 75);
        // handles all things enemy related
        for (int i = 0; i < Enemies.size(); i++) {
            boolean remove = false;
            boolean collison = false;
            for (Obstacle obs : obstacles) {
                // stops enemies from walking over obstacles, sometimes the enemies glitch through to add some difficulty
                if (obs.getX() < Enemies.get(i).getPos('X') + Enemies.get(i).getXSize() &&
                        obs.getX() + obs.getXSize() > Enemies.get(i).getPos('X') &&
                        obs.getY() < Enemies.get(i).getPos('Y') + Enemies.get(i).getYSize() &&
                        obs.getY() + obs.getYSize() > Enemies.get(i).getPos('Y')) {
                    collison = true;
                    break;
                }
            }
            
            if (dist(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'), charX, charY) < 50 && !lostLife) {
                // allows player to lose hp, if player is within 50 pixels and isn't
                // invincible
                hp -= 10;
                lostLife = true;
            }
            if (swung && player.checkHit(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'),
                    Enemies.get(i).getXSize(), Enemies.get(i).getYSize())) {
                    //if enemies are hit by a sword, kill them at end of loop
                    Item item = player.getInventory().getItem();
                if (Enemies.get(i).hit(item.getDamage(), item.getType())) {
                    remove = true;
                }
            }

            Enemies.get(i).render();
            //display enemies

            if (collison) {
                //stop enemy from moving if it hits an obstacle.
                Enemies.get(i).moveBack(i);
                continue;
                
            }
            Enemies.get(i).move(charX, charY);

            if (remove) {
                Enemies.remove(i);
            }
        }

        // if the player is invincible, make a timer increase
        if (lostLife) {
            iFrames++;
        }
        // if it has been 3 seconds (180 frames) since the player has lost a life,
        // resets the timer and makes it vulnerable
        if (iFrames >= 30) {
            lostLife = false;
            iFrames = 0;
        }
        ArrayList<Obstacle> broken = new ArrayList<>(); // keeps track of all broken obstacles

        for (Obstacle obs : obstacles) {
            //sees whether obstacles have been hit, and if their hp drops below 0 destroys them
            if (swung && player.checkHit(obs.getX(), obs.getY(), obs.getXSize(), obs.getYSize())) {
                Item item = player.getInventory().getItem();
                if (obs.hit(item.getDamage(), item.getType())) {
                    broken.add(obs);
                }
            }
            obs.render();

        }
        obstacles.removeAll(broken);
        player.move(); // moves player

        fill(255);
        rect(width - 130, 20, 150, 50);
        fill(0);
        textSize(50);
        text("Save", width - 25, 75);
        fill(255);

    }

    public static void addObs(Obstacle obs) {
        obstacles.add(obs);
    }

    public void menu() {
        background(bg);
        textAlign(LEFT);
        ellipse(width - 60, 45, 50, 50);
        fill(150);
        rect((width / 2) - 250, (height / 2) - 100, 500, 200);
        textSize(100);
        fill(0);
        text("Play", (width / 2) - 100, (height / 2) + 35);

        fill(255);
        rect(width / 2 - 100, height / 2 + 150, 210, 50);
        fill(0);
        textSize(50);
        text("Reset", width / 2 - 100, height / 2 + 195);

        textSize(50);
        text("i", width - 67, 63);
        text("High Score: " + highScore, 30, 63);
        fill(255);

    }

    public void instructions() {
        background(bg);
        textSize(75);
        text("1. Move with WSAD", 50, 75);
        text("2. Hit with the Space Bar", 50, 135);
        text("3. Num keys to change inventory slot", 50, 195);
        text("4. Place with the space bar", 50, 255);
        text("5. Stay alive as long as possible", 50, 315);

        rect(width - 90, 20, 50, 50);
        fill(0);
        textSize(50);
        text("H", width - 80, 63);
        fill(255);
    }

    // handles all keyboard inputs
    public void keyPressed() {
        player.keyPressed(key); //passes key to player
        if (key == 'l') {
            hp = 0;
        }
    }

    // when movement keys are lifted, stops the char from moving
    public void keyReleased() {
        player.keyReleased(key); //passes key to player
    }

    // handles when the mouse is clicked
    public void mouseClicked() {
        // different actions depending on what scene is active
        if (gameCode == 0) {
            if (mouseX > 500 && mouseX < 1000 && mouseY > 400 && mouseY < 600) {
                gameCode = 2; // starts game
            } else if (mouseX > 700 && mouseX < 890 && mouseY > 650 && mouseY < 800) {
                Loading.reset(this); //resets the game manually
            } else if (dist(mouseX, mouseY, width - 60, 45) < 51) {
                gameCode = 1; // goes to instructions

            }
            // funny wat of showing why else if is important
        } else if (gameCode == 1) {
            if (mouseX > 1400 && mouseX < 1450 && mouseY > 20 && mouseY < 70) {
                gameCode = 0; // goes home
            }
        } else if (gameCode == 2) {
            if (mouseX > 1370 && mouseX < 1500 && mouseY > 20 && mouseY < 70) {
                Loading.save(Player.getInv(), obstacles, charX, charY); // saves the game so you can leave
            }
        }

    }

    public static int getHighScore() {
        return highScore;
    } 
    public static void setHighScore(int score) {
        highScore = score;
    }

    public static int getDay() {
        return dayNum;
    }
    public static void setDay(int num) {
        dayNum = num;
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
