package inputs;

import core.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    private Window w;
    public KeyboardHandler(Window w) {
        this.w= w;
        w.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean movingLeft = false; // tránh truong hop chuyen huong nhanh giu 2 phim bị đứng im
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D){
            w.player.velx = w.player.speed;
            movingLeft = false;
        }
        if(key == KeyEvent.VK_A){
            w.player.velx = -w.player.speed;
            movingLeft = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D && !movingLeft){
            w.player.velx =0;
        }
        if(key == KeyEvent.VK_A && movingLeft){
            w.player.velx =0;
        }

    }
}
