import Interfaces.Tools;
import processing.core.PApplet;
import processing.core.PImage;

public class Tool implements Tools {
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


    // chatGPT techanccly made this, but i had to panstakily get it poit of it step by step and the negative value handling i did myself.
    public boolean checkCollision(int enemyX, int enemyY, int enemyWidth, int enemyHeight) {
        int x1Min = X;
        int x1Max = X + xSize;
        int y1Min = Y;
        int y1Max = Y + ySize;

        if (x1Min > x1Max) {
            int temp = x1Min;
            x1Min = x1Max;
            x1Max = temp;
        }
        if (y1Min > y1Max) {
            int temp = y1Min;
            y1Min = y1Max;
            y1Max = temp;
        }

        System.out.println("x1Min: " + x1Min + ", x1Max: " + x1Max + ", y1Min: " + y1Min + ", y1Max: " + y1Max);

        // Calculate bounds for Square 2
        int x2Min = enemyX;
        int x2Max = enemyX + enemyWidth;
        int y2Min = enemyY;
        int y2Max = enemyY + enemyHeight;

        System.out.println("enemyX: " + enemyX + ", enemyY: " + enemyY + ", enemyWidth: " + enemyWidth + ", enemyHeight: " + enemyHeight);

        System.out.println("x2Min: " + x2Min + ", x2Max: " + x2Max + ", y2Min: " + y2Min + ", y2Max: " + y2Max);



        if (x2Min > x2Max) {
            int temp = x2Min;
            x2Min = x2Max;
            x2Max = temp;
        }
        if (y2Min > y2Max) {
            int temp = y2Min;
            y2Min = y2Max;
            y2Max = temp;
        }

        boolean xOverlap = x1Max >= x2Min && x1Min <= x2Max;
        boolean yOverlap = y1Max >= y2Min && y1Min <= y2Max;

        // System.out.println(xOverlap +" "+yOverlap);

        return xOverlap && yOverlap;


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

        c.image(img, X, Y, xSize, ySize);
        // c.rect(X, Y, xSize, ySize);

    }

    public void hide() {
        X = Integer.MAX_VALUE;
        Y = Integer.MAX_VALUE;
    }

    public String getType() {
        return type;
    }
    public String getTool() {
        return type;
    }
}