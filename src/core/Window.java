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
    private KeyboardHandler kListener = new KeyboardHandler(this);
    public LevelHandler level = new LevelHandler(this);

    // game object

    public Window(String title){
        JFrame frame = new JFrame(title);

        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(true);
        frame.setVisible(true);
        frame.add(this);
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

    //algothium calc FPS and logic game
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates =0;
        int frames = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates ++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS : " + frames +" ticks : " + updates);
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
        level.render(g);

        g.setColor(Color.RED);
        g.fillRect(0,300+41 , this.getWidth(),5);

        bs.show();
        g.dispose();

    }
}
