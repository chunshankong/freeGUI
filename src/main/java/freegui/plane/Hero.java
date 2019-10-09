package freegui.plane;

import freegui.Context;
import freegui.Sprite;
import freegui.Window;
import util.Animation;
import util.AssertUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class Hero extends Sprite {

    private Animation animationNormal;
    private BufferedImage tx;

    public Hero(int x, int y, int width, int height) {
        super(x, y, width, height);
//        region = AssertUtil.getImageRegion("/assert/image/1.png",220,0,220,220);
        //normal
        tx = AssertUtil.getImage("/assert/image/plane/tx.jpg");
        animationNormal = new Animation();
        animationNormal.setDuration(500);
        animationNormal.setKeyFrames(new BufferedImage[]{
                AssertUtil.getImage("/assert/image/plane/hero-blue-0.png"),
                AssertUtil.getImage("/assert/image/plane/hero-blue-1.png")
        });

    }

    @Override
    public void afterEndLive() {

    }

    @Override
    protected void update() {

    }
    public static int count;

    @Override
    protected void draw(Context context) {
//        context.setColor(Color.PINK);
//        context.drawRect(x,y,width,height,true);
        context.drawImage(animationNormal.getKeyFrame(),x,y,width,height);
//        context.setColor(Color.RED);
//        context.drawString(count+"dd",x-30,y-30,30);

        context.drawImage(tx,x,y+100,100,100);

    }

    @Override
    protected void up() {
        y-=10;
    }

    @Override
    protected void down() {
        y+=10;
    }

    @Override
    protected void left() {
        x-=10;
    }

    @Override
    protected void right() {
        x+=10;
    }

    @Override
    protected void space() {
        count++;
        Window.addSprite(new Wave(x,y,21,60));
    }

    @Override
    public void keyDown(int keyCode) {
        super.keyDown(keyCode);
        if (KeyEvent.VK_ENTER == keyCode) {
            Window.addTask(1000L, () -> {
                Window.addSprite(new Wave(x,y,200,200));
            });
        }
    }
}
