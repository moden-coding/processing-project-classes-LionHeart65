import processing.core.PApplet;
import processing.core.PImage;

public class Rock implements Interfaces.Obstacle {
    private int X;
    private int Y;
    private int hp = 5;
    private PApplet c;
    private PImage img;
    private String breaktool = "Pickaxe";
    private int xSize = 50;
    private int ySize = 50;
    private int iFrames = 31;
    private int frames = 0;
    private boolean onCooldown = false;


    public Rock(int X, int Y, PApplet c) {
        this.X = X;
        this.Y = Y;
        this.img = c.loadImage("src/Assets/rock.jpeg");
        this.c = c;
    }

    public void render() {
        if (onCooldown) {
            frames++;
            if (frames >= iFrames) {
                frames = 0;
                onCooldown = false;
            }
        }

        c.image(img, Y, X, xSize, ySize);


        if (hp <= 0) {
        }
        // System.out.println("X " + X + " Y " + Y);
    }

    public boolean hit(int damage, String tool) {
        if (onCooldown == false && breaktool.equals(tool)) {

            hp -= damage;
        }
        onCooldown = true;

        System.out.println("HIT!!!");
        System.exit(0);
        if (hp <=0 ) {
            return true;
        } else {
            return false;
        }

    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }


    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    

}
