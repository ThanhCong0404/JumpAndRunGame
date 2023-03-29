package level;

import core.GameState;
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

    public LinkedList<Item> removing = new LinkedList<Item>();
    private int seed;

    public int startingX = 100, startingY = 200;

    public double cameraX=0, cameraY=0;
    public double gameSpeed = 1;

    public int lastestSectorX = 0; //toa do x khu vuc moi nhat
    public int sectorWidth = 0;

    public Player player = null;


    public LevelHandler(Window w) {
        this.w = w;
        this.sectorWidth = Window.width*2; //set độ dài cho sector
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

    //generate khu vực(vùng chơi) tiếp theo cho player
    public void generateSector(int sectorX,int sectorWidth){
        lastestSectorX = sectorX+sectorWidth; //set lai toa do x cuối của khu vực chơi dc generate sau cùng
        //generate platform
        for(int x = sectorX; x < sectorX+sectorWidth ;x++){
            int maxW = 600, minW =100;
            Random r = new Random();
            //add platform
            int w = r.nextInt(maxW-minW) + minW;
            int y = 400 - r.nextInt(100);
            items.add(new Platform(ObjectIDs.platform,x,y,w,2,Color.BLUE));
            //add in spikes
            int spikeChance = 2; // để tính tỉ lệ xuất hiện
            if(r.nextInt(spikeChance) == 0){
                int spikeW = 32;
                int maxSpikes = w/spikeW; // tối đa 1 platform có thể chứa số lượng spike
                int amountOfSpikes = r.nextInt(maxSpikes);
                if(amountOfSpikes != 0){
                    for(int spikeX = x+spikeW; spikeX < x+w-spikeW; spikeX+= w/amountOfSpikes){ //spkeX +=w/amountOfSpikes : để chia đều vị trí spike trên platform
                        if(r.nextBoolean()) { //tránh trường hợp không có chổ đứng
                            items.add(new Spike(ObjectIDs.spike,spikeX,y-32,32,32,new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)) ));
                        }
                    }
                }
            }else { //generate goblin
                if(r.nextBoolean()) { //tránh trường hợp không có chổ đứng
                    items.add(new Goblin(this.w,ObjectIDs.goblin,x+30,y-30,30,30, player.baseSpeed));
                }
            }

            //generate khoảng trống nối tiếp platform
            int maxG = 300 , minG = 20;
            int nextPlatformGap = w + r.nextInt(maxG-minG) + minG;
            x+=nextPlatformGap;
        }

    }

    public void render(Graphics g){
        g.translate(-(int)cameraX,-(int)cameraY);
        for(Item i : items){
            i.render(g);
        }
        if(player!=null) player.render(g);

        g.translate((int)cameraX,(int)cameraY);

        //render game over menu
        if(w.gs == GameState.GameOver){
            g.setColor(Color.black);
            FontMetrics fontMetrics = g.getFontMetrics(g.getFont());
            g.drawString("GAME OVER!!!", Window.width/2-fontMetrics.stringWidth("GAME OVER!!!")/2,Window.height/2);
            g.drawString("Nhấm phím Space để tiếp tục...", Window.width/2-fontMetrics.stringWidth("Nhấm phím Space để tiếp tục...")/2,Window.height/2+64);


        }

    }

    public void tick(){
        gameSpeed += 0.005;
        cameraX += gameSpeed;

        if(cameraX+ sectorWidth > lastestSectorX){ //tao ra vô hạn khu vực tiếp theo
            generateSector(lastestSectorX,sectorWidth);
        }

        for(Item i : items){
            i.tick();
        }
        for(Item i : removing){
            items.remove(i);
        }
        if(player!=null) player.tick();
    }

    //remove items from the game
    public void removeItem (Item i){
        removing.add(i);
    }

    public void loseGame(){
        w.gs = GameState.GameOver;
    }

    public void restartLevel() {

        generateLevel(); // khoi tao lai man choi

        player.x = startingX;
        player.y = startingY;
        cameraX = 0;
        cameraY = 0;
        gameSpeed = 1;

        w.gs = GameState.Game;
    }
}
