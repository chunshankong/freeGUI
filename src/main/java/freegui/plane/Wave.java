package freegui.plane;

import freegui.Context;
import freegui.Sprite;
import freegui.Window;
import util.AssertUtil;
import java.awt.image.BufferedImage;

public class Wave extends Sprite {
    private BufferedImage normal;

    public Wave(int x, int y, int width, int height) {
        super(x, y, width, height);
        normal = AssertUtil.getImage("/assert/image/plane/wave.png");
    }

    @Override
    protected void afterEndLive() {
        Window.addSprite(new Explode(x,y,100,100));
        super.dispose();
    }

    @Override
    protected void update() {
        y -= 3;
        if (y <= 0){
            super.dispose();
        }
    }

    @Override
    protected void draw(Context context) {
        context.drawImage(normal,x,y,width,height);
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
