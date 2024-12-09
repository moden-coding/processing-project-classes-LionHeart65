import processing.core.PApplet;
import processing.core.PImage;


public class RockItem implements Interfaces.Item {
    private PImage img;
    private PApplet c;
    
    public RockItem(int X, int Y, PApplet c) {
        this.img = c.loadImage("src/Assets/swords.png");
        this.c = c;

    }

    public String getType() {
        return "we";
    }

    public PImage getImg() {
        return img;
    }
    
    public void render() {
        
    }
    
    public void hide() {

    }

}
