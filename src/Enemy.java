
import processing.core.PApplet;
import processing.core.PVector;

public class Enemy {
    private PVector position = new PVector(0, 0); // current pos.
    private PVector charXY; // pos of player
    private PApplet c; // main canvas
    private float speed = 1.5f;
    private float selfX = 0;
    private float selfY = 0;
    private int health = values(0);
    private int[] colors = { values(1), values(2), values(3) }; // color of enemies. should be static?
    private int xSize = values(4);
    private int ySize = values(5);
    private int iFrames = 31; // stops the enemy from dying in a few frames
    private int frames = 0;
    private boolean onCooldown = false;

    public Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.selfX = X;
        this.selfY = Y;

    }

    public int getXSize() {
        return xSize;
    }
    public int getYSize() {
        return ySize;
    }

    public void moveBack(int num) {
        //if hits an obstacle, moves back a bit so its not stuck, random because doesn't know what side its hitting
        if (num % 2 == 0) {
            if (num % 4 == 0) {
                selfX += 1;
            } else {
                selfX -= 1;
            }
        } else {
            if (num % 3 == 0) {
                selfY += 1;
            } else {
                selfX -= 1;
            }
        }
    }

    // can't set different values in subclasses, so values are set here,
    // and then variables assigned to functions calls
    public int values(int value) {
        switch (value) {
            // health
            case 0:
                return 5;
            // colors
            case 1:
                return 27;
            case 2:
                return 102;
            case 3:
                return 4;
            // need to return something
            //x and y sizes
            case 4:
                return 10;
            case 5:
                return 40;
            default:
                return 0;

        }
    }

    public int getHealth() {
        return health;
    }

    // finds direction between the character and the enemy, then makes the enemy
    // move along that vector.
    public void move(int charX, int charY) {
        if (onCooldown) {
            frames++;
            if (frames >= iFrames) {
                frames = 0;
                onCooldown = false;
            }
        }
        position = new PVector(selfX, selfY);

        charXY = new PVector(charX, charY);

        PVector direction = PVector.sub(charXY, position);
        direction.normalize();
        direction.mult(speed);
        position.add(direction);
        selfX = position.x;
        selfY = position.y;
        
    }

    public boolean hit(int damage, String type) {
        // if enemy is hit, removes health, returns true if the enemy has died
        if (onCooldown == false) {
            if (type.equals("weapon")) {
                health -= damage;
            }
        }
        onCooldown = true;
        if (health <= 0 ) {
            return true;
        } else {
            return false;
        }

    }

    public void render() {
        // displays the enemy
        c.fill(colors[0], colors[1], colors[2]);
        c.rect(selfX, selfY, 10, 40);
        c.fill(255);
    }
    // gets pos of enemy to deal with in the App file.
    public int getPos(char axis) {
        if (axis == 'X') {
            return Math.round(selfX);
        } else if (axis == 'Y') {
            return Math.round(selfY);
        } else {
            return 0;
        }
    }

        
}