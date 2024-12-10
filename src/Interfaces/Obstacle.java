package Interfaces;

public interface Obstacle {
    
    public void render();
    public boolean hit(int damage, String tool);
    public int getX();
    public int getY();
    public int getXSize();
    public int getYSize();
}
