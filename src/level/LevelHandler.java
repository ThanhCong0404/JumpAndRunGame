package level;

import core.Window;
import objects.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

// màn chơi
public class LevelHandler {
    private Window w;
    public double gravity = 4;

    public LinkedList<Item> items = new LinkedList<Item>();
    private int seed;
    public double cameraX=0, cameraY=0;

    public Player player = null;


    public LevelHandler(Window w) {
        this.w = w;
        //set random
        Random r = new Random();
        seed = r.nextInt();

        //add in player
        player = new Player( w ,100,100,42,42);


        //generate level
        items.add(new Platform(ObjectIDs.platform,100,100,200,2, Color.RED));
        items.add(new Platform(ObjectIDs.platform,400,100,200,2, Color.RED));
        items.add(new Platform(ObjectIDs.platform,600,300,200,2, Color.RED));
        items.add(new Platform(ObjectIDs.platform,0,400,10000,2, Color.RED));
        items.add(new Spike(ObjectIDs.spike,300,100,22,22, Color.RED));

    }
    public void render(Graphics g){
        g.translate(-(int)cameraX,-(int)cameraY);
        for(Item i : items){
            i.render(g);
        }
        player.render(g);
        g.translate((int)cameraX,(int)cameraY);
    }

    public void tick(){
        cameraX += 0.1;
        for(Item i : items){
            i.tick();
        }
        player.tick();
    }

}
