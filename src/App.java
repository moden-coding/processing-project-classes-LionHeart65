import java.util.ArrayList;

import processing.core.PApplet;

public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int lr = 1; // is char facing left or right, 1 is right, -1 is left
    int ud = 1; // is char facing up or down, 1 is up, -1 is down
    float speed = 1; // speed of the player, adjusted to move diagonally
    float speedStat = 3; // raw speed of the player
    int bg = color(59, 47, 30);
    float shootAngle = 0;
    int gameCode = 0; // what scene is the game on, 0 is menu, 1 is instructions, 2 is game
    int hp = 100;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    boolean moveXPos = false; // is the player moving in these directions
    boolean moveXNeg = false;
    boolean moveYPos = false;
    boolean moveYNeg = false;
    int damage = 1;
    int iFrames = 0; // frames since player lost a life.
    boolean lostLife = false; // has the player lost a life within 3 secs

    

    public void settings() { // Sets the background size
        size(1500, 1000);
    }
    public void setup() {
        charX = width / 2; // sets player starting coords.
        charY = height / 2;
        background(bg);

    }

    // following methods get random (X, Y) values to spawn Enemies in random
    // locations.
    public int[] randCoord() {
        int[] coords = new int[2];
        if (random(1) > 0.5) {
            float X = random(0, 50);
            coords[0] = Math.round(X);
        } else {
            float X = random(width-50, width);
            coords[0] = Math.round(X);
        }
        float Y = random(0, height);
        coords[1] = Math.round(Y);
        return coords;
    }

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

    // main game function, where the playing happens. 1
    public void play() {

        background(bg);
        charAndWea();
        fill(0);
        textSize(35);

        
        // moves the player
        if (moveXPos) {// D
            charX += speed;
        }
        if (moveXNeg) {// A
            charX -= speed;
        }
        if (moveYPos) { // S
            charY += speed;
        }
        if (moveYNeg) { // W
            charY -= speed;
        }
        // prevents the player from going super fast if moving diagonally.
        if ((moveXPos || moveXNeg) && (moveYPos || moveYNeg)) {
            speed = speedStat / sqrt(2);
        } else {
            speed = speedStat;
        }

        // handles all things enemy related
        for (int i = 0; i < Enemies.size(); i++) {

            Enemies.get(i).move(charX, charY);

            // allows player to lose hearts, if player is within 50 pixels and isn't
            // invincible
            if (dist(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'), charX, charY) < 50 && !lostLife) {
                hp -= 10;
                lostLife = true;
            }


        }
        // next two if statments gives three seconds of invincibility after losing a
        // life

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

    // creates the character and the gun on the screen
    public void charAndWea() {
        fill(145, 125, 80);
        rect(charX, charY, 20, 50);
        fill(181, 167, 91);
        ellipse(charX + 10, charY, 15, 15);
        // makes the character and gun face the right ways
        fill(117, 112, 99);
        if (ud == 1) {
            rect(charX + 5, charY + 20, 5, -30);
        } else if (ud == -1) {
            rect(charX + 5, charY + 20, 5, 30);

        } else if (ud == 0) {
            rect(charX + 5, charY + 20, 30 * lr, 5);
        }

        // what to do when player runs out of hearts; resets all stats, the char pos,
        // and removes all enemies and
        if (hp <= 0) {
            iFrames = 0;
            lostLife = false;
            gameCode = 0;
            charX = width / 2;
            charY = height / 2;
            hp = 100;
            Enemies.clear();
        }
        fill(255);
    }

    // handles all keyboard inputs
    public void keyPressed() {

        // next 4 if statments make the char move, the gun shoot the right way, and the
        // char and gun face
        // the right way.
        if (key == 'w') {

            moveYNeg = true;
            ud = 1;

            // shootAngle = (float) 4.71239;

        } else if (key == 's') {

            moveYPos = true;
            ud = -1;

            // shootAngle = (float) 1.5708;

        } else if (key == 'a') {
            moveXNeg = true;
            lr = -1;
            ud = 0;

            // shootAngle = (float) 3.14159;

        } else if (key == 'd') {
            moveXPos = true;
            lr = 1;
            ud = 0;

            // shootAngle = 0;

            // shoots if the spacebar is pressed
        } else if (key == ' ') {
        }

    }

    // when movement keys are lifted, stops the char from moving
    public void keyReleased() {
        if (key == 'w') {

            moveYNeg = false;

        } else if (key == 's') {

            moveYPos = false;

        } else if (key == 'a') {
            moveXNeg = false;

        } else if (key == 'd') {
            moveXPos = false;
        }
    }

    // handles when the mouse is clicked
    public void mouseClicked() {
        // different actions depending on what scene is active
        if (gameCode == 0) {
            if (mouseX > 500 && mouseX < 1000 && mouseY > 400 && mouseY < 600) {
                gameCode = 2; // starts game
            }
        }

    }
    public static void main(String[] args) {
        PApplet.main("App");
    }
}


