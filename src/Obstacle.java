import Interfaces.Item;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Obstacle implements Interfaces.Obstacles {
    private int X;
    private int Y;
    private int hp = 5;
    private PApplet c;
    private PImage img;
    private String breaktool = "Pickaxe";
    private int xSize = 25;
    private int ySize = 25;
    private int iFrames = 31;
    private int frames = 0;
    private boolean onCooldown = false;
    private Item dropItem;


    public Obstacle(int X, int Y, PApplet c, Item dropItem, String breaktool, String img) {
        this.X = X;
        this.Y = Y;
        this.img = c.loadImage(img);
        this.c = c;
        this.breaktool = breaktool;
        this.dropItem = dropItem;
    }


    int num = 0;
    public void render() {
        
        if (onCooldown) {
            frames++;
            if (frames >= iFrames) {
                frames = 0;
                onCooldown = false;
            }
        }

        c.image(img, X, Y, xSize, ySize);


        if (hp <= 0) {
            
            Player.addItem(dropItem);
        }
    }

    public boolean hit(int damage, String tool) {
        if (onCooldown == false && breaktool.equals(tool)) {

            hp -= damage;
        }
        onCooldown = true;

        if (hp <=0 ) {
            return true;
        } else {
            return false;
        }

    }

    public int getHP() {
        return hp;
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

    public abstract String name();



    

}
