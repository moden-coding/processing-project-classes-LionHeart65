import processing.core.PApplet;


public class StoneItem extends MaterialItem {
    private PApplet c;
    public StoneItem(PApplet c, String code) {
        super(c, "src/Assets/stone.jpeg", code);

    }

    
    public void place(int X, int Y) {
        App.addObs(new Stone(X, Y, c));
        System.out.println("placed");
    }

}