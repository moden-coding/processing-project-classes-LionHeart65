import processing.core.PApplet;

public class Rock extends Obstacle {
        


    public Rock(int X, int Y, PApplet c) {
        super(X, Y, c, new StoneItem(c, "Rock"), "Pickaxe", "src/Assets/rock.jpeg");
        
    }
    

}
