import processing.core.PApplet;

public class Player extends PApplet {

    public void move() {
        fill(145, 125, 80);
        rect(charX, charY, 20, 50);
        fill(181, 167, 91);
        ellipse(charX + 10, charY, 15, 15);
        // makes the character and gun face the right ways
        fill(117, 112, 99);
        if (ud == 1) {
            rect(charX + 5, charY + 20, 5, -30);
        } else if (ud == -1) {
            rect(charX + 5, charY + 20, 5, 30);

        } else if (ud == 0) {
            rect(charX + 5, charY + 20, 30 * lr, 5);
        }
    }
}
