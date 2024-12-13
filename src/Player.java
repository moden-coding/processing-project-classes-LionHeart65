import java.util.ArrayList;

import Interfaces.Item;
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
    // private Sword sword;
    private static ArrayList<InventorySlot> inventory = new ArrayList<>();
    private int inventorySlot = 1;

    public Player(int X, int Y, PApplet c) {
        this.X = X;
        this.Y = Y;
        this.c = c;
        Sword sword = new Sword(X, Y, c);
        Pick pickaxe = new Pick(X, Y, c);
        inventory.add(new InventorySlot(sword, 0, 1));
        inventory.add(new InventorySlot(pickaxe, 1, 1));
    }

    public void move() {
        App.syncCoords(X, Y);
        // sword.setPlayerValues(X, Y, lr, ud);
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
        loadInventory();
    }

    public void lrPlayer() {
        // Draw the player
        c.fill(145, 125, 80);
        c.rect(X, Y, 20, 50);
        c.fill(181, 167, 91);
        c.ellipse(X + 10, Y, 15, 15);

    }

    public void udPlayer() {
        // Draw the player
        c.fill(145, 125, 80);
        c.rect(X, Y, 50, 20);
        c.fill(181, 167, 91);
        c.ellipse(X - 3, Y + 10, 15, 15);
    }

    public void loadInventory() {
        c.stroke(0);
        c.strokeWeight(3);
        for (int i = 0; i < 9; i++) {
            c.rect(525 + 55 * i, 900, 50, 50);
        }
        c.fill(179, 169, 116);
        c.rect(525 + 55 * inventorySlot, 900, 50, 50);
        c.strokeWeight(1);

        for (InventorySlot slot : inventory) {
            int slotNum = slot.getSlot();
            c.image(slot.getImg(), 533f + 55 * slotNum, 907.5f, 35f, 35f);
            if (slot.getSlot() == inventorySlot) {
                slot.getItem().render();
                if (App.swung) {
                    if (slot.getItem() instanceof MaterialItem) {
                    MaterialItem mat = (MaterialItem) slot.getItem();
                    mat.place(X, Y);
                    }
                }

            }
            if (slot.getItem() instanceof Tool) {
                Tool tool = (Tool) slot.getItem();
                tool.setPlayerValues(X, Y, lr, ud);
            } else if (slot.getItem() instanceof Item) {
                Item item = (Item) slot.getItem();
                item.setPlayerValues(X, Y, lr, ud);

            }
            //chatGPT loaded the number,
            int itemNum = slot.getNum();
            c.fill(0); // Black text color
            c.textSize(30); // Adjust size as needed
            c.textAlign(PApplet.RIGHT, PApplet.BOTTOM); // Align text to bottom-right
            c.text(itemNum, 577 + 55 * slotNum, 950); // Adjust position to bottom-right
            

        }

    }

    public boolean checkHit(int X, int Y, int enemyX, int enemyY) {
        if (inventory.size() - 1 >= inventorySlot) {
            if (inventory.get(inventorySlot).getItem() instanceof Interfaces.Tools) {
                
                Interfaces.Tools tool = (Interfaces.Tools) inventory.get(inventorySlot).getItem(); // chatGPT made  the casting thingw  
                return tool.checkCollision(X, Y, enemyX, enemyY);
            }
            return false;

        }
        return false;
    }

    public static void addItem(Item item) {
        for (InventorySlot slot : inventory) {
            if (slot.getItem().getClass() == item.getClass()) {
                slot.addItem();
                return;
            }
        }

        inventory.add(new InventorySlot(item, inventory.size(), 1));
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
            App.swing();
        } else if (key >= '1' && key <= '9') { // Check if the key is between '1' and '9'
            inventorySlot = key - '1'; // Convert char to int (e.g., '1' -> 1)
            // chatGPT made key switch logic
        }

    }

    public InventorySlot getInventory() {
        return inventory.get(inventorySlot);
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