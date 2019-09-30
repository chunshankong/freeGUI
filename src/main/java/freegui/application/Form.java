package freegui.application;

import javax.swing.*;
import java.awt.*;

public class Form {

    private JFrame jFrame;
    public Form(int x,int y,int width,int height){

        jFrame = new JFrame("form");
        jFrame.setBounds(x,y,width,height);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));



    }
    public void addComponent(Component component){

        jFrame.getContentPane().add(component);
        jFrame.repaint();
    }
    public void show(){
        jFrame.setVisible(true);
    }
}
