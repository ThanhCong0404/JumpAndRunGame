package objects;

import core.Window;

import java.awt.*;

public class Goblin extends Item{
    private Item standingOn = null; // check globin đứng trên platform
    private Window w;

    private boolean goingLeft = false;
    private double speed = 1;


    public Goblin(Window w,byte id, double x, double y, int width, int height,double speed) {
        super(id, x, y, width, height);
        this.w =w;
        this.speed = speed;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x,(int)y,width,height);
    }

    @Override
    public void tick() {
        //cơ chế rơi neu chua va cham platform
        if(standingOn == null){
            y+= w.level.gravity;

            //check neu va cham voi platform cho standingOn
            for(Item i : w.level.items){
                if(i.id == ObjectIDs.platform){
                    if(new Rectangle((int)x, (int)y,width,height).intersects(new Rectangle((int)i.x,(int)i.y,i.width,i.height))){
                        standingOn = i;
                    }
                }
            }
        }else{
            y = standingOn.y-height;
            //di chuyen qua lai tren platform
            if(x+width >= standingOn.x+standingOn.width || x <= standingOn.x ) {
                goingLeft = !goingLeft;
            }

            if(goingLeft){
                x-=speed;

            }else {
                x+=speed;
            }
        }


    }
}
