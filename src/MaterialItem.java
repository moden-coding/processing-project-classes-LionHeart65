import processing.core.PApplet;
import processing.core.PImage;


public abstract class MaterialItem implements Interfaces.Item {
    private PImage img;
    protected PApplet c;
    private int X;
    private int Y;
    private int playerX;
    private int playerY;
    private int lr;
    private int ud;
    private int ySize;
    private int xSize;

    public abstract void place(int X, int Y);
    public MaterialItem(PApplet c, String img) {
        this.c = c;
        this.img = c.loadImage(img);
        

    }

    public abstract String name();
    public void setPlayerValues(int X, int Y, int lr, int ud) {
        playerX = X;
        playerY = Y;
        this.lr = lr;
        this.ud = ud;
    }

    public int getDamage() {
        return 0;
    }

    public String getType() {
        return "material";
    }

    public PImage getImg() {
        return img;

    }
    
    private void setValues(int X, int Y, int width, int height) {
        this.X = X;
        this.Y = Y;
        xSize = width;
        ySize = height;

    }
    public void render() {
        if (ud == 0) {
                if (lr == -1) {
                    this.setValues(playerX + 4, playerY + 5, -20, 20);
                } else if (lr == 1) {
                    this.setValues(playerX + 16, playerY + 5, 20, 20);
                }
            
        } else {
                if (ud == -1) {
                    this.setValues(playerX + 45, playerY + 13, -20, 20);
                } else if (ud == 1) {
                    this.setValues(playerX + 8, playerY - 2, 20, 20);
                }
            
        }
        c.image(img, X, Y, xSize, ySize);

    }
    
    

}
