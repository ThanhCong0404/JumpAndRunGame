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
    public double cameraGain = 1; // toc do di chuyen camera

    public int lastestSectorX = 0; //toa do x khu vuc moi nhat
    public int sectorWidth = 0;

    public Player player = null;


    public LevelHandler(Window w) {
        this.w = w;
        this.sectorWidth = w.getWidth()*2; //set độ dài cho sector
        generateLevel();
    }

    public void generateLevel(){
        items = new LinkedList<Item>();
        //set random
        Random r = new Random();
        seed = r.nextInt();

        //add in player
        player = new Player( w ,startingX,startingY,42,42);

        generateSector(0,sectorWidth);
    }

    //tạo khu vực tiếp theo cho player
    public void generateSector(int sectorX,int sectorWidth){
        lastestSectorX = sectorX;
        //generate platform
        for(int x = sectorX; x < sectorX+sectorWidth ;x++){
            int maxW = 600, minW =100;
            Random r = new Random();
            int w = r.nextInt(maxW-minW) + minW;
            items.add(new Platform(ObjectIDs.platform,x,400,w,2,Color.BLUE));

            //generate khoảng trống nối tiếp platform
            int maxG = 300 , minG = 20;
            int nextPlatformGap = w + new Random().nextInt(maxG-minG) + minG;
            x+=nextPlatformGap;
        }

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
        if(cameraX+ sectorWidth > lastestSectorX){ //tao ra vô hạn khu vực tiếp theo
            generateSector(lastestSectorX,sectorWidth);
        }

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

        generateLevel(); // khoi tao lai man choi
    }
}
