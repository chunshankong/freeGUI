package freegui.plane;

import freegui.Context;
import freegui.Sprite;
import freegui.Window;
import util.Animation;
import util.AssertUtil;

import java.awt.image.BufferedImage;

public class Explode extends Sprite {

    private Animation explode;
    static BufferedImage [] ex;
    public Explode(int x, int y, int width, int height) {
        super(x, y, width, height);
        if (ex == null){
            ex = AssertUtil.parseOneDimensionalArray(
                    AssertUtil.split(AssertUtil.getImage("/assert/image/plane/explode-3d.png"),4,4));
        }
        explode = new Animation();
        explode.setKeyFrames(ex);
        explode.setPlayMode(Animation.PlayMode.NORMAL);
        explode.setDuration(100);
    }

    @Override
    protected void afterEndLive() {
        Window.addTask(5000L,()->{
            super.dispose();
            System.out.println(this);
        });
    }

    @Override
    protected void update() {
        if (explode.isFinished()){
            endLive();
        }
    }

    @Override
    protected void draw(Context context) {
        context.drawImage(explode.getKeyFrame(),x,y,width,height);
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
