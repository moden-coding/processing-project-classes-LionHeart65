package Interfaces;
import processing.core.PImage;

public interface Item {
    void render();
    PImage getImg();
    String getType();
    int getDamage();
    void setPlayerValues(int X, int Y, int lr, int ud);
    String name();
    
}

