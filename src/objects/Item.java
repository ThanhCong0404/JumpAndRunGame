package objects;

import java.awt.*;

public abstract class Item {

    protected byte id;
    protected double x,y;
    protected int width,height;

    public Item(byte id,double x, double y, int width, int height) {
        this.id = id;
        this.x= x ;
        this.y = y;
        this.width= width;
        this.height = height;
    }

    public abstract void render(Graphics g);
    public abstract void tick();
}
