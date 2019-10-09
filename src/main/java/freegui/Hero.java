package freegui;

import util.AssertUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hero extends Sprite {

    private BufferedImage body ;

    public Hero(int x, int y, int width, int height) {

        super(x, y, width, height);
        body = AssertUtil.getImage("/assert/image/missile.png");
    }

    @Override
    public void afterEndLive() {

    }

    protected void update() {

    }

    protected  void draw(Context context) {
//        context.setColor(Color.RED);
//        context.drawRect(x,y,width,height,false);
//        context.drawImage(body,x,y,width,height);
        context.setColor(Color.WHITE);
        context.drawString("你们好吗哈哈哈哈哈哈觉得觉得都dddddssawrtt15933",x,y+50,20);
        context.drawRect(x,y,width,height,false);
    }

    protected void up() {
        this.y -= 10;
    }

    protected void down() {
        this.y +=10;
    }

    protected void left() {
        x -= 10;
    }

    protected void right() {
        x += 10;
    }

    @Override
    protected  void space() {
        Window.addSprite(new Wave(x,y,10,10));
    }

    @Override
    public void keyDown(int keyCode) {
        super.keyDown(keyCode);
    }
}
