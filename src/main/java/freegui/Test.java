package freegui;



import com.sun.jna.platform.FileUtils;
import sun.misc.IOUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {

        System.out.println("test");

        Window.createWindow(100,100);
//Window.createWindow();
        Hero hero = new Hero(0,0,100,100);
        Window.addSprite(hero);


//        Hero hero2 = new Hero(200,20);
//        Window.addSprite(hero2);

        Board board = new Board(200,150,20,100);
        Window.addSprite(board);

        Board board2 = new Board(200,280,20,100);
        Window.addSprite(board2);


    }


}

