import processing.core.PApplet;

public class Sword extends Tool {

    public Sword(int X, int Y, PApplet c) {
        super(X, Y, c, "src/Assets/swords.png", "weapon");

    }
    
    @Override public int getDamage() {
        return 5;
    }

    public String name() {
        return "sword";
    }

}