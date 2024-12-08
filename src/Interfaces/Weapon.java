package Interfaces;

public interface Weapon extends Item {
    boolean checkCollision(int X, int Y, int enemyX, int enemyY);
}