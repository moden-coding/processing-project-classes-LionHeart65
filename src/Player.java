import java.util.ArrayList;

import processing.core.PApplet;

public class Player {
    private int X;
    private int Y;
    private int lr = 1; // 1 is right, -1 is left
    private int ud = 1; // 1 is up, -1 is down, 0 is horizontal
    private float speed = 3; // raw speed of the player
    private boolean moveXPos = false; // is the player moving in these directions
    private boolean moveXNeg = false;
    private boolean moveYPos = false;
    private boolean moveYNeg = false;
    private PApplet c;
    private float PI = 3.1415926f;
    private Sword sword;
    private ArrayList<String[]> inventory = new ArrayList<>();
    private int inventorySlot = 1;

    public Player(int X, int Y, PApplet c) {
        this.X = X;
        this.Y = Y;
        this.c = c;
        sword = new Sword(X, Y, c);
        inventory.add(new String[] { "Sword", "1", "1", });

    }

    public void move() {
        App.syncCoords(X, Y);
        // Update position based on movement states
        if (moveXPos) {
            X += speed;
        }
        if (moveXNeg) {
            X -= speed;
        }
        if (moveYPos) {
            Y += speed;
        }
        if (moveYNeg) {
            Y -= speed;
        }
        // Adjust speed for diagonal movement
        if ((moveXPos || moveXNeg) && (moveYPos || moveYNeg)) {
            speed = 3 / PApplet.sqrt(2);
        } else {
            speed = 3;
        }

        if (ud == 0) {
            lrPlayer();
        } else {
            udPlayer();
        }

    }

    public void lrPlayer() {
        // Draw the player
        c.fill(145, 125, 80);
        c.rect(X, Y, 20, 50);
        c.fill(181, 167, 91);
        c.ellipse(X + 10, Y, 15, 15);

        if (App.getSwung() == false) {
            if (lr == -1) {
                sword.render(X + 4, Y + 5, -5, 35);
            } else if (lr == 1) {
                sword.render(X + 16, Y + 5, 5, 35);
            }
        } else {
            if (lr == -1) {
                sword.render(X + 4, Y + 20, -35, 5);
            } else if (lr == 1) {
                sword.render(X + 16, Y + 20, 35, 5);
            }
        }
        // inventory
        c.stroke(0); // Set the stroke color to black (border)
        c.strokeWeight(3); // Set the thickness of the border
        for (int i = 0; i < 9; i++) {
            c.rect(525 + 55 * i, 900, 50, 50);
        }
        c.fill(179, 169, 116);

        c.rect(525 + 55 * inventorySlot, 900, 50, 50);

        c.strokeWeight(1);
    }

    public void udPlayer() {
        // Draw the player
        c.fill(145, 125, 80);
        c.rect(X, Y, 50, 20);
        c.fill(181, 167, 91);
        c.ellipse(X - 3, Y + 10, 15, 15);

        // Draw the gun based on direction

        if (App.getSwung() == false) {
            if (ud == -1) {
                sword.render(X + 45, Y + 13, -35, 5);
            } else if (ud == 1) {
                sword.render(X + 8, Y - 2, 35, 5);
            }
        } else {
            if (ud == -1) {
                sword.render(X + 16, Y + 15, -5, 35);
            } else if (ud == 1) {
                sword.render(X + 16, Y + 5, 5, -35);
            }
        }
        c.strokeWeight(3); // Set the thickness of the border

        for (int i = 0; i < 9; i++) {
            c.rect(525 + 55 * i, 900, 50, 50);
        }
        c.fill(179, 169, 116);
        c.rect(525 + 55 * inventorySlot, 900, 50, 50);
        c.strokeWeight(1);

    }

    public void swing() {
        App.swing();
    }

    public void keyPressed(char key) {
        // Update movement states based on key press
        if (key == 'w') {
            moveYNeg = true;
            ud = 1;
        } else if (key == 's') {
            moveYPos = true;
            ud = -1;
        } else if (key == 'a') {
            moveXNeg = true;
            lr = -1;
            ud = 0;
        } else if (key == 'd') {
            moveXPos = true;
            lr = 1;
            ud = 0;
        } else if (key == ' ') {
            swing();
        } else if (key >= '1' && key <= '9') { // Check if the key is between '1' and '9'
            inventorySlot = key - '1'; // Convert char to int (e.g., '1' -> 1)
            //chatGPT made key switch logic
        }
    

    }

    public void keyReleased(char key) {
        // Update movement states based on key release
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

}