package objects;

import java.awt.*;

public abstract class Item {

    protected byte id;

    public Item(byte id) {
        this.id = id;
    }

    public abstract void render(Graphics g);
    public abstract void tick();
}
