package objects;
import level.LevelHandler;

import java.awt.*;
//animation dấu vết kí tự cho player
// vệt lưu bóng mờ dần đến khi mất and removing
public class CharacterTrail extends Item{
    public double opacity = 255;
    public LevelHandler level;
    public CharacterTrail(byte id, LevelHandler level, double x, double y, int width, int height) {
        super(id, x, y, width, height);
        this.level = level;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255,0,0,(int)opacity));
        g.fillRect((int)x,(int)y,width,height);
    }

    @Override
    public void tick() {
        opacity -= (25- level.gameSpeed); //tạo vết dài hơn theo gameSpeed
        if(opacity<0) level.removeItem(this);
    }
}
