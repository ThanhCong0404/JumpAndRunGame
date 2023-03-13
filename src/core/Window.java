package core;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas implements Runnable {

    public Window(String title){
        JFrame frame = new JFrame(title);

        frame.setSize(800,600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(true);
        frame.setVisible(true);
        frame.add(this);
    }

    @Override
    public void run() {

    }
}
