import java.util.ArrayList;

import Interfaces.Item;
import Interfaces.Obstacle;
import processing.core.PApplet;

public class App extends PApplet {

    Player player;
    static int charX = 0;
    static int charY = 0;
    int lr = 1; // is char facing left or right, 1 is right, -1 is left
    int ud = 1; // is char facing up or down, 1 is up, -1 is down
    float speed = 1; // speed of the player, adjusted to move diagonally
    float speedStat = 3; // raw speed of the player
    int bg = color(46, 89, 61);
    float shootAngle = 0;
    int gameCode = 2; // what scene is the game on, 0 is menu, 1 is instructions, 2 is game
    int hp = 100;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    boolean moveXPos = false; // is the player moving in these directions
    boolean moveXNeg = false;
    boolean moveYPos = false;
    boolean moveYNeg = false;
    int damage = 1;
    int iFrames = 0; // frames since player lost a life.
    boolean lostLife = false; // has the player lost a life within 3 secs
    static boolean swung;
    int swingTime = 15;
    int swingFrames = 0;
    static ArrayList<Obstacle> obstacles = new ArrayList<>();
    

    
    public static void swing() {
        swung = true;
    }
    public static boolean getSwung() {
        return swung;
    }

    
    

    public void settings() { // Sets the background size
        size(1500, 1000);
    }
    public void setup() {
        charX = width / 2; // sets player starting coords.
        charY = height / 2;
        background(bg);
        player = new Player(width / 2, height / 2, this);


    }

    // following methods get random (X, Y) values to spawn Enemies in random
    // locations.
    public int[] randCoord() {
        int[] coords = new int[2];
        coords[0] = Math.round(random(50, width - 50)); // Ensure within visible bounds
        coords[1] = Math.round(random(50, height - 50)); // Ensure within visible bounds
        return coords;
    }

;
    public void draw() {
        // decides which scene to show based on game code value.
        switch (gameCode) {
            case 0:
                menu();
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
    int num = 1;
    // main game function, where the playing happens. 1
    public void play() {
        background(bg);
        if (swung) {
            swingFrames++;
            if (swingFrames >= swingTime) {
                swingFrames = 0;
                swung = false;
            }
        }

        if (num == 1) {
            Enemies.add(new Enemy(randCoord()[0], randCoord()[1], this));
            Enemies.add(new Enemy(1000, 500, this));

            obstacles.add(new Rock(randCoord()[0], randCoord()[1], this));
            obstacles.add(new Rock(randCoord()[0], randCoord()[1], this));

            obstacles.add(new Rock(randCoord()[0], randCoord()[1], this));
            obstacles.add(new Rock(randCoord()[0], randCoord()[1], this));

            num++;
        }
        

        // handles all things enemy related
        for (int i = 0; i < Enemies.size(); i++) {
            Enemies.get(i).move(charX, charY);

            // allows player to lose hp, if player is within 50 pixels and isn't
            // invincible
            if (dist(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'), charX, charY) < 50 && !lostLife) {
                hp -= 10;
                lostLife = true;
            }
            if (swung && player.checkHit(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'), Enemies.get(i).getXSize(), Enemies.get(i).getYSize())) {
                Item item = player.getInventory().getItem();
                if (Enemies.get(i).hit(item.getDamage(), item.getType())) {
                    Enemies.remove(i);
                }

            }
        }

        // if the player is invincable, make a timer increase
        if (lostLife) {
            iFrames++;
        }
        // if it has been 3 seconds (180 frames) since the player has lost a life,
        // resets the timer and makes it vulnerable
        if (iFrames >= 180) {
            lostLife = false;
            iFrames = 0;
        }
        ArrayList<Obstacle> broken = new ArrayList<>();

        for (Obstacle obs : obstacles) {
            if (swung && player.checkHit(obs.getX(), obs.getY(), obs.getXSize(), obs.getYSize())) {
                Item item = player.getInventory().getItem();
                if (obs.hit(item.getDamage(), item.getType())) {
                    broken.add(obs);
                }
            }
            obs.render();

        }
        obstacles.removeAll(broken);
        player.move();

    }

    public static void addObs(Obstacle obs) {
        obstacles.add(obs);
    }

    public void menu() {
        background(bg);
        ellipse(width - 60, 45, 50, 50);
        fill(150);
        rect((width / 2) - 250, (height / 2) - 100, 500, 200);
        textSize(100);
        fill(0);
        text("Play", (width / 2) - 100, (height / 2) + 35);

    }

    public void instructions() {
        background(bg);
        textSize(75);
        text("1. Move with WSAD", 50, 75);
        text("2. Shoot with the Space Bar", 50, 135);
        rect(width - 90, 20, 50, 50);
        fill(0);
        textSize(50);
        text("H", width - 80, 63);
        fill(255);
    }

    // handles all keyboard inputs
    public void keyPressed() {
        player.keyPressed(key);
    }

    // when movement keys are lifted, stops the char from moving
    public void keyReleased() {
        player.keyReleased(key);
    }

    // handles when the mouse is clicked
    public void mouseClicked() {
        // different actions depending on what scene is active
        if (gameCode == 0) {
            if (mouseX > 500 && mouseX < 1000 && mouseY > 400 && mouseY < 600) {
                gameCode = 2; // starts game
            }
        }
        System.out.println("X " + mouseX + " Y " + mouseY);
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}


