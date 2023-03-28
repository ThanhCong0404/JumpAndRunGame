package core;

import inputs.KeyboardHandler;
import level.LevelHandler;
import objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends Canvas implements Runnable {

    private Thread thread;
    private boolean running = false;
    public KeyboardHandler kListener = new KeyboardHandler(this);

    public static final int width = 800 , height = 600;
    public LevelHandler level;

    // game object

    public Window(String title){
        JFrame frame = new JFrame(title);

        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(true);
        frame.setVisible(true);
        frame.add(this);

        level = new LevelHandler(this);
    }

    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        running = false;
        try {
            thread.join(); //stop
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //algothium calc fps and logic game
    @Override
    public void run() {
        long lastTime = System.nanoTime(); // thời gian lần cập nhật trước nanosecond.
        double amountOfTicks = 60.0; // số lần cập nhật(tick) trong một giây (tick /second)
        double ns = 1000000000 / amountOfTicks; // tính khoảng thời gian giữa mỗi lần cập nhật(tick()) theo đơn vị nanosecond, chia 1 giây (10^9) cho số lượng tick trong một giây
        double delta = 0; //lưu trữ số lượng thời gian đã trôi qua giữa các lần cập nhật
        long timer = System.currentTimeMillis(); //gán tạm 1 giá trị thời gian trả về để làm mốc
        int updates =0; // số lần cập nhật,tick()
        int frames = 0; //số khung hình được vẽ trong 1s

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; //delta sẽ được cộng với thời gian trôi qua giữa lần cập nhật trước và lần cập nhật hiện tại chia cho ns, để tính toán số lần cập nhật(tick) cần thực hiện
            lastTime = now;
            // đồng bộ giữa hai hoạt động là "tick" và "render"
            while(delta >= 1){
                tick();
                updates++;
                delta--; //giảm delta xuống 1 để tiếp tục update cho lần tiếp theo
            }
            render();

            frames++;
            if(System.currentTimeMillis() - timer > 1000){ //  đo số lượng updates và frames đã được thực hiện trong 1 giây.
                timer += 1000;
                System.out.println("fps : " + frames +" ticks : " + updates);
                frames =0;
                updates=0;
            }

        }
        stop();
    }

    public void tick(){
        level.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(2);
            bs = this.getBufferStrategy();
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.PINK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        //====//
        //render màn  chơi
        level.render(g);


        bs.show();
        g.dispose();

    }
}
