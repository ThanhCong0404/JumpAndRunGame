package inputs;

import core.GameState;
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
        if(w.gs == GameState.Game){
            //go back menu
            if((key == KeyEvent.VK_ESCAPE)){
                w.gs =GameState.Menu;
                SoundHandler.clip.stop();
            }



            if((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) && w.level.player.canMoveRight){
                w.level.player.velx = w.level.player.realSpeed();
                movingLeft = false;
            }
            if((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) && w.level.player.canMoveLeft){
                w.level.player.velx = -w.level.player.realSpeed();
                movingLeft = true;
            }
            if((key == KeyEvent.VK_W || key == KeyEvent.VK_UP)){
                WDown = true;
            }

            if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
                if(w.level.player.Falling){
                    if(w.level.player.vely < 0){ // stop jump
                        w.level.player.vely = 0;
                    }else{ //going down
                        w.level.player.quickFall = true;
                    }
                }
            }
        }else if( w.gs == GameState.Menu){
            w.menu.KeyPress(key);
        }else if( w.gs == GameState.Options){
            w.options.KeyPress(key);
        }else if( w.gs == GameState.GameOver){
            if(key == KeyEvent.VK_SPACE){
                w.level.restartLevel();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(w.gs == GameState.Game){

            if((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) && !movingLeft){
                w.level.player.velx =0;
            }
            if((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) && movingLeft){
                w.level.player.velx =0;
            }
            if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
                WDown = false;
            }
        }else if( w.gs == GameState.Menu){

        }


    }
}
