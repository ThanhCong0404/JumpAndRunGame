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

    public int startingX = 100, startingY = 200;

    public double cameraX=0, cameraY=0;
    public double cameraGain = 0.5; // toc do di chuyen camera

    public Player player = null;


    public LevelHandler(Window w) {
        this.w = w;
        //set random
        Random r = new Random();
        seed = r.nextInt();

        //add in player
        player = new Player( w ,startingX,startingY,42,42);


        //generate level
        items.add(new Platform(ObjectIDs.platform,100,100,200,2, Color.BLUE));
        items.add(new Platform(ObjectIDs.platform,400,100,200,2, Color.BLUE));
        items.add(new Platform(ObjectIDs.platform,600,300,200,2, Color.BLUE));
        items.add(new Platform(ObjectIDs.platform,0,400,10000,5, Color.BLUE));
        items.add(new Spike(ObjectIDs.spike,300,400-32,32,32, Color.BLUE));
        items.add(new Spike(ObjectIDs.spike,340,400-32,32,32, Color.BLUE));

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
        cameraX += cameraGain;
        for(Item i : items){
            i.tick();
        }
        player.tick();
    }

    public void restartLevel() {
        player.x = startingX;
        player.y = startingY;
        cameraX = 0;
        cameraY = 0;
    }
}
