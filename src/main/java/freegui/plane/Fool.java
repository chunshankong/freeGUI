package freegui.plane;

import freegui.Context;
import freegui.Sprite;
import freegui.Window;
import util.Animation;
import util.AssertUtil;

import java.awt.image.BufferedImage;

public class Fool extends Sprite {
    private Animation normal;
    private   BufferedImage [] buff;

    public Fool(int x, int y, int width, int height) {
        super(x, y, width, height);


            BufferedImage[][] bufferedImages = AssertUtil.split(AssertUtil.getImage("/assert/image/plane/fool.png"),1,3);
            buff = bufferedImages[0];

        normal = new Animation();
        normal.setDuration(60);
        normal.setKeyFrames(buff);
    }

    @Override
    protected void afterEndLive() {

    }

    @Override
    protected void update() {

        Wave wave = Window.getCollisionWithClass(this,Wave.class);
        if (wave != null){
            y -= 3;
            wave.endLive();
        }

    }

    @Override
    protected void draw(Context context) {
        context.drawImage(normal.getKeyFrame(),x,y,width,height);
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
