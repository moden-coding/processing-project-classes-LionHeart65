import Interfaces.Tools;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Tool implements Tools {
    private int X;
    private int Y;
    private int playerX;
    private int playerY;
    private PApplet c;
    private int xSize;
    private int ySize;
    private PImage img;
    private int lr = 1;
    private int ud = 0;
    private String type;

    public Tool(int X, int Y, PApplet c, String imgURL, String type) {
        this.X = X + 5;
        this.Y = Y + 4;
        this.c = c;
        this.img = c.loadImage(imgURL);
        this.type = type;
    }

    public int getDamage() {
        return 0;
    }


    // chatGPT technically made this, but i had to painstaking tell it step by step and the negative value handling i did myself.
    public boolean checkCollision(int enemyX, int enemyY, int enemyWidth, int enemyHeight) {
        int x1Min = Math.min(X, X + xSize);
        int x1Max = Math.max(X, X + xSize);
        int y1Min = Math.min(Y, Y + ySize);
        int y1Max = Math.max(Y, Y + ySize);

        // Calculate bounds for Square 2
        int x2Min = Math.min(enemyX, enemyX + enemyWidth);
        int x2Max = Math.max(enemyX, enemyX + enemyWidth);
        int y2Min = Math.min(enemyY, enemyY + enemyHeight);
        int y2Max = Math.max(enemyY, enemyY + enemyHeight);

        boolean xOverlap = x1Max >= x2Min && x1Min <= x2Max;
        boolean yOverlap = y1Max >= y2Min && y1Min <= y2Max;

        return xOverlap && yOverlap;
    }



    private void setValues(int X, int Y, int width, int height) {
        //tells the tool where to render
        this.X = X;
        this.Y = Y;
        xSize = width;
        ySize = height;

    }

    public PImage getImg() {
        return img;
    }

    public void setPlayerValues(int X, int Y, int lr, int ud) {
        //tells the tool where the player is
        playerX = X;
        playerY = Y;
        this.lr = lr;
        this.ud = ud;
    }


    public void render() {
        if (ud == 0) {
            if (!App.getSwung()) {
                if (lr == -1) {
                    this.setValues(playerX + 4, playerY + 5, -5, 35);
                } else if (lr == 1) {
                    this.setValues(playerX + 16, playerY + 5, 5, 25);
                }
            } else {
                if (lr == -1) {
                    this.setValues(playerX + 4, playerY + 20, -35, 5);
                } else if (lr == 1) {
                    this.setValues(playerX + 16, playerY + 20, 35, 5);
                }
            }
        } else {
            if (!App.getSwung()) {
                if (ud == -1) {
                    this.setValues(playerX + 45, playerY + 13, -35, 5);
                } else if (ud == 1) {
                    this.setValues(playerX + 8, playerY - 2, 35, 5);
                }
            } else {
                if (ud == -1) {
                    this.setValues(playerX + 16, playerY + 15, -5, 35);
                } else if (ud == 1) {
                    this.setValues(playerX + 16, playerY + 5, 5, -35);
                }
            }
        }

        c.image(img, X, Y, xSize, ySize);
    }

    

    public String getType() {
        return type;
    }
    public String getTool() {
        return type;
    }
}