import processing.core.PApplet;
import processing.core.PImage;

public class Rock implements Interfaces.Obstacle {
    private int X;
    private int Y;
    private int hp = 5;
    private PApplet c;
    private PImage img;

    public Rock(int X, int Y, PApplet c) {
        this.X = X;
        this.Y = Y;
        this.img = c.loadImage("src/Assets/rock.jpeg");
        this.c = c;
    }

    public void render() {
        c.image(img, Y, X, 50, 50);
    }

    public void hit(int damage) {
        hp -= damage;
    }

}
