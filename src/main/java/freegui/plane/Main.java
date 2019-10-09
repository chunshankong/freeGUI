package freegui.plane;

import freegui.FpsLabel;
import freegui.Window;



public class Main {

    public static void main(String[] args) {
        System.out.println("万江珂雷霆战机");

        Window.createWindow(0,0,800,800);
        Window.setTitle("万江珂的雷霆战机程序");
//        Window.setHighQuality(false);
        Window.addSprite(new BackGround(0,0,800,800));


        Window.addSprite(new Hero(200,400,100,100));

        Window.addSprite(new Fool(100,220,100,100));
        Window.addSprite(new Fool(200,220,100,100));
        Window.addSprite(new Fool(300,220,100,100));
        Window.addSprite(new Fool(400,220,100,100));
        Window.addSprite(new Fool(500,220,100,100));
        Window.addSprite(new Fool(600,220,100,100));

        Window.addSprite(new FpsLabel(310,100,100,100));

    }




}
