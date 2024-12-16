import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Interfaces.Item;
import processing.core.PApplet;


public class Loading {

    
    private static String saveFile = "src/save.bin";
    

    public static void save(ArrayList<InventorySlot> inv, ArrayList<Obstacle> obstacles, int X, int Y) {
        File file = new File(saveFile);
        file.delete();
        try (FileWriter writer = new FileWriter(saveFile)) {
            for (InventorySlot slot : inv) {
                String newLine = String.format("%s,%s,%s\n", slot.getItem().name(), slot.getSlot(), slot.getNum());
                writer.write(newLine);
                
            }
            writer.write("Obstacles \n");
            for (Obstacle obs : obstacles) {
                String newLine = String.format("%s,%s,%s,%s\n", obs.name(), obs.getXSize(), obs.getY(), obs.getHP());
                writer.write(newLine);
            }
        } catch (Exception e) {
            System.out.println("Error Saving");
            return;
        }
    }

    public static void load(PApplet c) {
        try (Scanner scnr = new Scanner(Paths.get(saveFile))) {
            ArrayList<InventorySlot> newInv = new ArrayList<>();
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                if (line.equals("Obstacles")) {
                    break;
                }
                String[] data = line.split(",");

                Item newItem;
                if (data[0].equals("stone")) {
                    newItem = new StoneItem(c);
                } else if (data[0].equals("sword")) {
                    newItem = new Sword(0, 0, c);
                } else if (data[0].equals("pick")) {
                    newItem = new Pick(0, 0, c);
                }
                else {
                    newItem = new Sword(0,0, c);
                    throw new Exception("unknown Item");

                }
                System.out.println(newItem);
                newInv.add(new InventorySlot(newItem, Integer.valueOf(data[1]), Integer.valueOf(data[2])));
                
            }
        } catch (Exception e) {
            System.out.println("Error Loading: " + e);
            return;
        }
    }
} 

