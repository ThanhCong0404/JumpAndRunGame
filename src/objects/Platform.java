package objects;

import java.awt.*;

public class Platform extends Item {
    public Color c;

    public Platform(byte id, int x, int y, int width, int height, Color c) {
        super(id,x,y,width,height);

        this.c = c;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRoundRect((int)x,(int)y,width,height,12,12); // thêm bo góc
    }

    @Override
    public void tick() {

    }
}
