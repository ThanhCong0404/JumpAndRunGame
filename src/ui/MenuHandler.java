package ui;

import core.GameState;
import core.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuHandler {
    private Window w;

    private int selectorX=0, selectorY=0;
    private byte selector =0; // thứ tự

    private int selectorLeftBuffer = 16; //margin
    private int selectorWidth = 16; //độ rộng dấu select

    private int titleYOffSet=0; //animation String Title
    private boolean titleYOffsetAdd;

    public MenuHandler(Window w) {
        this.w=w;
    }

    public void render(Graphics g){
        g.setColor(Color.black);

        String strTitle = "Run And Jump", strStartGame= "Bắt Đầu",strOption = "Tùy Chọn", strExit ="Thoát";

        //change font for title
        g.setFont(new Font("Times New Roman",Font.BOLD,48));
        FontMetrics fontM = g.getFontMetrics(g.getFont());

        g.drawString(strTitle,Window.width/2 - (fontM.stringWidth(strTitle)/2) ,55+titleYOffSet);

        //change font normal
        g.setFont(new Font("Times New Roman",Font.BOLD,24));
        fontM = g.getFontMetrics(g.getFont());

        g.drawString(strStartGame,Window.width/2 - (fontM.stringWidth(strStartGame)/2) ,255);
        if(selector ==0) {
            selectorX = Window.width/2 - (fontM.stringWidth(strStartGame)/2)  -selectorWidth -selectorLeftBuffer;
            selectorY = 255-selectorWidth/2;
        }


        g.drawString(strOption,Window.width/2 - (fontM.stringWidth(strOption)/2),355);
        if(selector ==1) {
            selectorX = Window.width/2 - (fontM.stringWidth(strOption)/2)  -selectorWidth -selectorLeftBuffer;
            selectorY = 355-selectorWidth/2;
        }


        g.drawString(strExit,Window.width/2 - (fontM.stringWidth(strExit)/2),455);
        if(selector ==2) {
            selectorX = Window.width/2 - (fontM.stringWidth(strExit)/2)  -selectorWidth -selectorLeftBuffer;
            selectorY = 455-selectorWidth/2;
        }


        //selector
        g.setColor(Color.BLUE);
        if(selectorX !=0) g.fillOval(selectorX,selectorY,selectorWidth,selectorWidth);
    }

    // để update menu
    public void tick(){
        if(titleYOffsetAdd) {
            titleYOffSet += 1;
        }else {
            titleYOffSet -=1;
        }
        if(titleYOffSet< -100 || titleYOffSet >100){
            titleYOffsetAdd = !titleYOffsetAdd; // đổi hướng di chuyển animation
        }
    }

    // xử lý khi nhấn phím trên menu
    public void KeyPress(int key) {
        if((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) && selector < 3){
            selector += 1;
        }

        if((key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && selector >0){
            selector -= 1;
        }
        //new game
        if(key == KeyEvent.VK_ENTER  && selector ==0){
            w.GotoGame();
        }
        //exit
        if(key == KeyEvent.VK_ENTER  && selector ==2){
            System.exit(0);
        }
        //options
        if(key == KeyEvent.VK_ENTER  && selector ==1){
            w.gs = GameState.Options;
        }
    }
}
