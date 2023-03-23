package objects;

import core.Window;

import java.awt.*;

public class Player {
    public Window w;
    public int width,height;
    public int speed = 3; //van toc di chuyen
    public double x,y;
    public double velx , vely; // van toc,khoang cach moi lan di chuyen truc x , y

    public double jumpVelocity = 4; // van toc,do cao nhay

    public boolean Falling = false; // sự rơi
    public boolean Jumpable = false;

    public Player(Window w,double x,double y, int width , int height){
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

        //handle jumping
        if(w.kListener.WDown && Jumpable){
            vely = -jumpVelocity;
        }

        Collisions();

//        if(y+vely < 300){ // down
//        }else{
//            vely =0;
//        }
    }

    public void Collisions(){ // xu ly va cham
        Falling = true;
        Jumpable = false;


        for(Item i : w.level.items){
            //platform collisions
            if(i.id == ObjectIDs.platform){
                Platform p = (Platform) i;
                // phat hien va cham cho platform
                if(new Rectangle((int) (x+velx) , (int)y + (int) vely,width,height).intersects(p.x,p.y,p.width,p.height)){ //intersect : diem giao

                    //stop falling
                    if(y+height <= p.y+1){
                        Falling = false;
                        if(vely > 0){ // xu ly vi tri khi da nhay len 1 platform , set vi tri truc y
                            vely= 0;
                            y = p.y - height+1;
                        }
                    }else if( y < p.y ){ // dừng lại khi va chạm cạnh củab platform
                        velx = 0;
                    }

                    if (vely < 0 && y > p.y) { // xu ly va cham khi nhay len (vely so am la nhay)
                        Falling = true;
                        y-=(vely+1);
                        vely = -1 * vely; //dao nguoc huong
                    }
                }

                // phat hien va cham cho platform cho tuong lai
                float CollisionTimeDetectionTicks = 20;
                if(!Falling ||  ( Math.abs(y+height-p.y) <20 && new Rectangle((int)(x+velx* CollisionTimeDetectionTicks) , (int)(y+vely* CollisionTimeDetectionTicks),width,height).intersects(p.x,p.y,p.width,p.height))) { //intersect : diem giao
                    Jumpable = true;
                }
            }

            //Spike collisions
            if(i.id == ObjectIDs.spike){
                Spike s = (Spike) i;
                if(new Rectangle(s.x,s.y,s.width,s.height).intersects(new Rectangle((int)x,(int)y,width,height))){
                    w.level.restartLevel();
                }
            }
        }
    }

    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect((int)x,(int)y,width,height);

    }
}

