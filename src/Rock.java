import processing.core.PApplet;

public class Rock extends Obstacle {
    public Rock(int X, int Y, PApplet c, int hp) {
        super(X, Y, c, new StoneItem(c), "Pickaxe", "src/Assets/rock.jpeg", hp);
        
    }
    
    public String name() {
        return "rock";
    }

}
