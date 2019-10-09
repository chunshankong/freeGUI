package freegui.plane;

import freegui.Context;
import freegui.Sprite;
import util.AssertUtil;

import java.awt.image.BufferedImage;

public class BackGround extends Sprite {

    BufferedImage bg ;

    public BackGround(int x, int y, int width, int height) {
        super(x, y, width, height);
        bg = AssertUtil.getImageRegion("/assert/image/plane/bg.jpg",0,0,width,1536);
        this.y = y-bg.getHeight()/2;
    }

    @Override
    protected void afterEndLive() {

    }

    @Override
    protected void update() {
        this.y += 1;
        if(this.y >= 0)y = y-bg.getHeight()/2;
    }

    @Override
    protected void draw(Context context) {
//        context.setColor(Color.GRAY);
//        context.drawRect(x,y,width,height,true);
        context.drawImage(bg,x,y,width,bg.getHeight());

    }

    @Override
    protected void up() {

    }

    @Override
    protected void down() {

    }

    @Override
    protected void left() {

    }

    @Override
    protected void right() {

    }

    @Override
    protected void space() {

    }
}
