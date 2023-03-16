package objects;

import java.awt.*;

public class Platform extends Item {
    public int x,y,width,height;
    public Color c;

    public Platform(byte id, int x, int y, int width, int height, Color c) {
        super(id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.c = c;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect(x,y,width,height);


    }

    @Override
    public void tick() {

    }
}
