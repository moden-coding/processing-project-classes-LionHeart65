import processing.core.PApplet;


public class StoneItem extends MaterialItem {
    // private PApplet c;
    public StoneItem(PApplet c) {
        super(c, "src/Assets/stone.jpeg");

    }

    
    public void place(int X, int Y) {
        System.out.println(c);
        if (c == null) {
            System.out.println("whyyyyy");
            System.exit(0);
        }
        App.addObs(new Stone(X, Y, c));
        System.out.println("placed");
    }

    public String name() {
        return "Stone";
    }

}