import Interfaces.Item;
import processing.core.PImage;

public class InventorySlot {
    private Item Item;
    private int slot;
    private int num;
    private PImage img;

    public InventorySlot(Item item, int slot, int num) {
        this.Item = item;
        this.slot = slot;
        this.num = num;
        this.img = item.getImg();
    }

    public Item getItem() {
        return Item;
    }
    public PImage getImg() {
        return img;
    }

    public void addItem() {
        num++;
    }
    public void remItem() {
        num--;
    }

    public void changeNum(int changeNum) {
        num += changeNum;
    }
    public int getSlot() {
        return slot;
    }
    public int getNum() {
        return num;
    }


    
}
