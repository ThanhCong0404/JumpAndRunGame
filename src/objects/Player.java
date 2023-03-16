package objects;

import java.awt.*;

public class Player {
    public int x,y,width,height;
    public int velx , vely; // van toc x , y
    public int speed = 2;


    public Player(int x,int y, int width , int height){
        this.x = x ;
        this.y = y;
        this.width = width;
        this.height= height;

    }

    public void tick(){
        x+= velx;
        y+= vely;
    }

    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect(x,y,width,height);

    }
}

