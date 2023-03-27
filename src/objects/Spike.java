package objects;

import java.awt.*;

public class Spike extends Item{

    public Color c;

    public int[] PolyX = new int[3],PolyY = new int[3];
    public Polygon p; //đa giác

    public Spike(byte id, int x, int y, int width, int height, Color c) {
        super(id,x,y,width,height);

        PolyX[0] = x;
        PolyX[1] = x+width/2;
        PolyX[2] = x+width;

        PolyY[0] = y+height;
        PolyY[1] = y;
        PolyY[2] = y+height;

        p = new Polygon(PolyX,PolyY,3);


        this.c = c;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.drawPolygon(p);
    }

    @Override
    public void tick() {

    }
}
