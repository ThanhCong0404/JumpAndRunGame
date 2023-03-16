package level;

import objects.Item;
import objects.ObjectIDs;
import objects.Platform;
import objects.Spike;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class LevelHandler {
    public double gravity = 4;

    public LinkedList<Item> items = new LinkedList<Item>();
    private int seed;


    public LevelHandler() {
        Random r = new Random();
        seed = r.nextInt();
        items.add(new Platform(ObjectIDs.platform,100,100,200,2, Color.RED));
        items.add(new Platform(ObjectIDs.platform,400,100,200,2, Color.RED));
        items.add(new Platform(ObjectIDs.platform,600,300,200,2, Color.RED));
        items.add(new Spike(ObjectIDs.spike,300,100,22,22, Color.RED));

    }
    public void render(Graphics g){
        for(Item i : items){
            i.render(g);
        }
    }

    public void tick(){

    }

}
