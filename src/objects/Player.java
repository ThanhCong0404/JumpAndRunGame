package objects;

import core.Window;

import java.awt.*;

public class Player {
    public Window w;
    public int width,height;
    public double x,y;
    public double velx , vely; // van toc x , y

    public double jumpVelocity = 4; // van toc,do cao nhay
    public int speed = 2;


    public Player(Window w,int x,int y, int width , int height){
        this.w = w;
        this.x = x ;
        this.y = y;
        this.width = width;
        this.height= height;

    }

    public void tick(){
        x+= velx;

        if(vely < w.level.gravity){
            vely+= 0.1;
        }
        if(y+vely < 300){
            y+=vely;
        }else{
            vely =0;
        }
    }

    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect((int)x,(int)y,width,height);

    }
}

