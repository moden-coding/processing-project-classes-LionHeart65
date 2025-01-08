import processing.core.PApplet;


public class StoneItem extends MaterialItem {
    public StoneItem(PApplet c) {
        super(c, "src/Assets/stone.jpeg");

    }

    
    public void place(int X, int Y) {
        App.addObs(new Stone(X, Y, c, 3));
    }

    public String name() {
        return "stone";
    }

}