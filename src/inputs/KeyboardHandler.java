package inputs;

import core.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    private Window w;
    public boolean WDown = false;
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
            w.level.player.velx = w.level.player.speed;
            movingLeft = false;
        }
        if(key == KeyEvent.VK_A){
            w.level.player.velx = -w.level.player.speed;
            movingLeft = true;
        }
        if(key == KeyEvent.VK_W){
            if(w.level.player.Jumpable){ // khi nao dc phep nhay moi set nhan W
//                w.level.player.vely = -w.level.player.jumpVelocity;
                WDown = true;
            }
        }

        if(key == KeyEvent.VK_S){
            if(w.level.player.Falling){
                if(w.level.player.vely < 0){ // stop jump
                    w.level.player.vely = 0;
                }else{ //going down
                    w.level.player.vely = w.level.gravity *2;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D && !movingLeft){
            w.level.player.velx =0;
        }
        if(key == KeyEvent.VK_A && movingLeft){
            w.level.player.velx =0;
        }
        if(key == KeyEvent.VK_W){
            WDown = false;
        }


    }
}
