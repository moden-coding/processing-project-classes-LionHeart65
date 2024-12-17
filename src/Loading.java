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
            writer.write("Inventory\n");
            for (InventorySlot slot : inv) {
                String newLine = String.format("%s,%s,%s\n", slot.getItem().name(), slot.getSlot(), slot.getNum());
                writer.write(newLine);
                
            }
            writer.write("Obstacles\n");
            for (Obstacle obs : obstacles) {
                String newLine = String.format("%s,%s,%s,%s\n", obs.name(), obs.getX(), obs.getY(), obs.getHP());
                writer.write(newLine);
            }
            writer.write("Stats\n");
            writer.write(X+","+Y+"\n");
        } catch (Exception e) {
            System.out.println("Error Saving");
            return;
        }
    }

    public static void load(PApplet c) {
        try (Scanner scnr = new Scanner(Paths.get(saveFile))) {
            ArrayList<InventorySlot> newInv = new ArrayList<>();
            ArrayList<Obstacle> newObstacles = new ArrayList<>();
            scnr.nextLine();
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
                newInv.add(new InventorySlot(newItem, Integer.valueOf(data[1]), Integer.valueOf(data[2])));
                
            }
            Player.setInventory(newInv);
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                if (line.equals("Stats")) {
                    break;
                }
                String[] data = line.split(",");

                Obstacle newObs;
                int X = Integer.valueOf(data[1]);
                int Y = Integer.valueOf(data[2]);
                int hp = Integer.valueOf(data[3]);
                if (data[0].equals("stone")) {
                    newObs = new Stone(X, Y, c, hp);
                } else if (data[0].equals("rock")) {
                    newObs = new Rock(X, Y, c, hp);
                }
                else {
                    newObs = new Rock(0,0, c, 5);
                    throw new Exception("unknown obstacle");

                }
                newObstacles.add(newObs);
            }
            App.setObstacles(newObstacles);
            String[] coords = scnr.nextLine().split(",");
            App.setCoords(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));

        } catch (Exception e) {
            System.out.println("Error Loading: " + e);
            return;
        }
    }

    public static void reset(App c) {
        ArrayList<InventorySlot> newInv = new ArrayList<>();
        ArrayList<Obstacle> newObs =  new ArrayList<>();
        newInv.add(new InventorySlot(new Sword(0, 0, c), 0, 1));
        newInv.add(new InventorySlot(new Pick(0, 0, c), 1, 1));

        Player.setInventory(newInv);

        newObs.add(new Rock(c.randCoord()[0], c.randCoord()[1], c, 5));
        newObs.add(new Rock(c.randCoord()[0], c.randCoord()[1], c, 5));
        newObs.add(new Rock(c.randCoord()[0], c.randCoord()[1], c, 5));
        newObs.add(new Rock(c.randCoord()[0], c.randCoord()[1], c, 5));
        App.setObstacles(newObs);
        Loading.save(newInv, newObs, c.width / 2, c.height/2);

    }
}

