package Interfaces;
import processing.core.PImage;

public interface Item {
    void render();
    PImage getImg();
    void hide();
    String getType();
}

