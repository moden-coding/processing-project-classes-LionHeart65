import processing.core.PApplet;

public class Pick extends Tool {
    private int damage = 3;
    public Pick(int X, int Y, PApplet c) {
        super(X, Y, c, "src/Assets/Pickaxe.png", "Pickaxe");
    }
    
    public int getDamage() {
        return damage;
    }
}