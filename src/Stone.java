import processing.core.PApplet;

public class Stone extends Obstacle {

    public Stone(int X, int Y, PApplet c) {
        System.out.println(c);
        super(X, Y, c, new StoneItem(c), "Pickaxe", "src/Assets/stone.jpeg");
    
    }

    public String name() {
        return "stone";
    }

}