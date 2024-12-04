import processing.core.PApplet;

public class Sword {
    private int X;
    private int Y;
    private PApplet c;
    private int xSize;
    private int ySize;
    public Sword(int X, int Y, PApplet c) {
        this.X = X+5;
        this.Y = Y+4;
        this.c = c;
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
