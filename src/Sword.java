import Interfaces.Weapon;
import processing.core.PApplet;
import processing.core.PImage;

public class Sword implements Weapon {
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

    public Sword(int X, int Y, PApplet c) {
        this.X = X + 5;
        this.Y = Y + 4;
        this.c = c;
        this.img = c.loadImage("src/Assets/swords.png");
    }

    public boolean checkCollision(int enemyX, int enemyY, int enemyWidth, int enemyHeight) {
        if (X < enemyX + enemyWidth &&
                X + xSize > enemyX &&
                Y < enemyY + enemyHeight &&
                Y + ySize > enemyY) {
            return true;
        } else {
            return false;
        }
    }

    private void setValues(int X, int Y, int width, int height) {
        this.X = X;
        this.Y = Y;
        xSize = width;
        ySize = height;

    }

    public PImage getImg() {
        return img;
    }

    public void setPlayerValues(int X, int Y, int lr, int ud) {
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

        c.rect(X, Y, xSize, ySize);

    }

    public void hide() {
        X = Integer.MAX_VALUE;
        Y = Integer.MAX_VALUE;
    }
}