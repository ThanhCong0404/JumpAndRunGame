package ui;

import core.GameState;
import core.Window;
import inputs.SoundHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OptionHandler {
    private Window w;

    private int selectorX=0, selectorY=0;
    private byte selector =0; // thứ tự

    private int selectorLeftBuffer = 16; //margin
    private int selectorWidth = 16; //độ rộng dấu select

    public OptionHandler(Window w) {
        this.w=w;
    }

    public void render(Graphics g){
        g.setColor(Color.black);

        String strTitle = "Run And Jump", strSoundOn= "Bật âm thanh:" + ( (SoundHandler.muted) ? " Tắt" : " Bật"), strExit ="Trở lại";

        //change font for title
        g.setFont(new Font("Times New Roman",Font.BOLD,48));
        FontMetrics fontM = g.getFontMetrics(g.getFont());

        g.drawString(strTitle,Window.width/2 - (fontM.stringWidth(strTitle)/2) ,55);

        //change font normal
        g.setFont(new Font("Times New Roman",Font.BOLD,24));
        fontM = g.getFontMetrics(g.getFont());

        g.drawString(strSoundOn,Window.width/2 - (fontM.stringWidth(strSoundOn)/2) ,355);
        if(selector ==0) { // xử lý vị trí dấu select
            selectorX = Window.width/2 - (fontM.stringWidth(strSoundOn)/2)  -selectorWidth -selectorLeftBuffer;
            selectorY = 355-selectorWidth/2;
        }


        g.drawString(strExit,Window.width/2 - (fontM.stringWidth(strExit)/2),455);
        if(selector ==1) {
            selectorX = Window.width/2 - (fontM.stringWidth(strExit)/2)  -selectorWidth -selectorLeftBuffer;
            selectorY = 455-selectorWidth/2;
        }


        //selector
        g.setColor(Color.BLUE);
        if(selectorX !=0) g.fillOval(selectorX,selectorY,selectorWidth,selectorWidth);
    }

    // để update menu
    public void tick(){

    }

    // xử lý khi nhấn phím trên menu
    public void KeyPress(int key) {
        if((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) && selector < 3){
            selector += 1;
        }

        if((key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && selector >0){
            selector -= 1;
        }
        //âm thanh
        if(key == KeyEvent.VK_ENTER  && selector ==0){
            SoundHandler.muted = !SoundHandler.muted;
            if(SoundHandler.muted && SoundHandler.clip != null)SoundHandler.clip.stop();
        }
        //exit
        if(key == KeyEvent.VK_ENTER  && selector ==1){
            w.gs = GameState.Menu;
        }
    }
}
