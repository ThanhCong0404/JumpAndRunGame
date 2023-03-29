package objects;

import core.Window;

import java.awt.*;

public class Player {
    public Window w;
    public int width,height;
    public int baseSpeed = 3; //van toc di chuyen player
    public double x,y;
    public double velx , vely; // van toc di chuyen truc x , y mỗi tick();

    public double jumpVelocity = 4; // van toc,do cao nhay

    public boolean Falling = false; // sự rơi
    public boolean quickFall = false; // handle move down
    public boolean Jumpable = false;
    public boolean doubleJumping =false;

    public boolean canMoveLeft = false, canMoveRight = false;

    public int jumpAirTime = 0; // thời gian ở trên không trung
    public byte trailTimer ;



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

        //handle animation Character Trail for player
        trailTimer +=1;
        if(trailTimer >2){
            trailTimer =0;
            w.level.items.add(new CharacterTrail(ObjectIDs.characterTrail,w.level,x,y,width,height));
        }

        //logic trọng lực rơi sau khi jump
        double maxFallSpeed = (quickFall) ? w.level.gravity*2 : w.level.gravity;
        if(vely < maxFallSpeed && Falling ){ //falling
            if(quickFall && vely < 0.5) vely =0.5; // set tốc độ bắt đầu rơi khi move down
            if(!quickFall) vely+= 0.1;
            if(quickFall) vely+= 0.5;
        } else if(!Falling && vely > 0){
            vely = 0; //set lai van toc roi = 0
        }

        //handle jumping
        if(w.kListener.WDown){
            if(Jumpable){ //neu đã dc phep jump
                vely = -jumpVelocity;
            }else if(!doubleJumping && jumpAirTime > 30){ // (nếu chưa nhảy đôi và sau 30 ticks)
                vely= -jumpVelocity; // nhảy lần 2 (mỗi ticks y+= vely)

                doubleJumping =true;
            }
        }

        //check nếu đang trên không trung
        if(Falling) {
            jumpAirTime += 1;
        }

        //restart level in case die;
        if(y> Window.height){
            w.level.loseGame();
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
        canMoveRight=true;
        canMoveLeft=true;


        for(Item i : w.level.items){
            //platform collisions
            if(i.id == ObjectIDs.platform){
                Platform p = (Platform) i;
                // phat hien va cham cho platform
                Rectangle playerRectangle = new Rectangle((int) (x+velx) , (int)y + (int) vely,width,height);
                if(playerRectangle.intersects(p.x,p.y,p.width,p.height)){ //intersect : diem giao

                    //VELY
                    //stop falling
                    if(y+height <= p.y+1){ //chi khi > hon y platform+1 <=> đã đứng đc lên platform
                        // set variable when we hit the platform
                        Falling = false;
                        quickFall =false;
                        doubleJumping = false;
                        jumpAirTime =0;

                        if(vely > 0){ // xu ly vi tri khi da nhay len 1 platform , set vi tri truc y
                            vely= 0;
                            y = p.y - height+1;
                        }
                    }else if( y < p.y ){ // dừng lại khi không nhảy quay khỏi đc platform
                        velx = 0;
                    }

                    if (vely < 0 && y > p.y) { // xu ly va cham khi nhay len (vely so am la nhay)
                        Falling = true;
                        y-=(vely+1);
                        vely = -1 * vely; //dao nguoc huong
                    }
                    //End VELY

                    //VElX
                    if(playerRectangle.intersects(p.x,p.y+1,p.width,p.height-2)){
                        // dừng di chuyển nếu platform cản đường
                        if(p.x + p.width <= x ){
                            canMoveLeft = false;
                            velx = 0;
                        }
                        if(p.x >= x+width){
                            canMoveRight = false;
                            velx = 0;
                        }
                    }
                    //End VELX
                }

                // phat hien va cham cho platform o tuong lai
                //khi gan roi khoi platform k cho phep nhay neu vi tri du kien k nam tren platform
                float CollisionTimeDetectionTicks = 3; // dùng để tính toán vị trí của đối tượng hiện tại trong tương lai (toa do+ toc do * thoi gian vi tri du doan)
                if(!Falling &&  ( Math.abs(y+height-p.y) < 20 && new Rectangle((int)(x+velx* CollisionTimeDetectionTicks) , (int)(y+vely* CollisionTimeDetectionTicks),width,height).intersects(p.x,p.y,p.width,p.height))) { //intersect : diem giao
                    Jumpable = true; // được phép nhảy
                }
            }

            //Spike collisions
            if(i.id == ObjectIDs.spike){
                Spike s = (Spike) i;
                if(new Rectangle( (int)s.x,(int)s.y,s.width,s.height).intersects(new Rectangle((int)x,(int)y,width,height))){
                    w.level.loseGame();
                }
            }

            //Goblin collision
            if(i.id == ObjectIDs.goblin){

                if(new Rectangle( (int)i.x,(int)i.y,i.width,i.height).intersects(new Rectangle((int)x+1,(int)y +height-1 ,width-2,1))){
                    //attack
                    w.level.removeItem(i);
                }else if(new Rectangle( (int)i.x,(int)i.y,i.width,i.height).intersects(new Rectangle((int)x,(int)y,width,height))){
                    w.level.loseGame();
                }
            }
        }
    }

    // return real speed of player
    public double realSpeed(){
        return baseSpeed * w.level.gameSpeed;
    }

    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect((int)x,(int)y,width,height);

    }
}

