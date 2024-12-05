import processing.core.PApplet;
import processing.core.PImage;

public class Sword {
    private int X;
    private int Y;
    private PApplet c;
    private int xSize;
    private int ySize;
    private PImage img;
    public Sword(int X, int Y, PApplet c) {
        this.X = X+5;
        this.Y = Y+4;
        this.c = c;
        this.img = c.loadImage("src/Assets/swords.png");
    }

    private void setSizes(int[] sizes) {
        xSize = sizes[0];
        ySize = sizes[1];
    }

    public void render(int X, int Y, int xScale, int yScale) {
        setSizes(new int[] {xScale, yScale});
        c.rect(X, Y, xSize, ySize);

    }

}
