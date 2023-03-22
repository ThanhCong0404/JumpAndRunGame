package objects;

import core.Window;

import java.awt.*;

public class Player {
    public Window w;
    public int width,height;
    public int speed = 2; //van toc di chuyen
    public double x,y;
    public double velx , vely; // van toc,khoang cach moi lan di chuyen truc x , y

    public double jumpVelocity = 4; // van toc,do cao nhay

    public boolean Falling = false; // sự rơi

    public Player(Window w,int x,int y, int width , int height){
        this.w = w;
        this.x = x ;
        this.y = y;
        this.width = width;
        this.height= height;

    }

    public void tick(){
        x+= velx;
        y+=vely;


        if(vely < w.level.gravity && Falling ){ //jump up
            vely+= 0.1;
        } else if(!Falling && vely > 0){
            vely = 0;
        }

        Collisions();

//        if(y+vely < 300){ // down
//        }else{
//            vely =0;
//        }
    }

    public void Collisions(){ // xu ly va cham
        Falling = true;

        for(Item i : w.level.items){
            if(i.id == ObjectIDs.platform){
                Platform p = (Platform) i;
                if(new Rectangle((int)x , (int)y + (int) vely,width,height).intersects(p.x,p.y,p.width,p.height)){ //intersect : diem giao
                    if(vely > 0) Falling = false;
                    if(vely > 0){ // xu ly vi tri khi da nhay len 1 platform , set vi tri truc y
                        y = p.y - height;
                    }
                    if (vely < 0) { // xu ly va cham khi nhay len (vely so am la nhay)

                        y-=(vely+1);
                        vely = -1 * vely; //dao nguoc huong
                    }
                }
            }
        }
    }

    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect((int)x,(int)y,width,height);

    }
}

