package freegui;

import java.awt.*;

public class Board extends Sprite{

    public Board(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void afterEndLive() {

    }

    @Override
    protected void update() {
        Wave sprite = Window.getCollisionWithClass(this,Wave.class);

        if (null != sprite){
            System.out.println(sprite);
            x += 30;
            System.out.println("+");
        }
    }

    @Override
    protected  void draw(Context context) {
        context.setColor(Color.GRAY);
        context.drawRect(x,y,width,height,true);
    }

    @Override
    protected  void up() {

    }

    @Override
    protected  void down() {

    }

    @Override
    protected  void left() {

    }

    @Override
    protected  void right() {

    }

    @Override
    protected   void space() {

    }
}
