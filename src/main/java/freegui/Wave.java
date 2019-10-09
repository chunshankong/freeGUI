package freegui;

public class Wave extends Sprite {
    public Wave(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    @Override
    public void afterEndLive() {

    }

    @Override
   protected void update() {
        x+=1;
    }

    @Override
    protected void draw(Context context) {
        context.drawOval(x,y,width,height,true);
    }

    @Override
    protected void up() {

    }

    @Override
    protected   void down() {

    }

    @Override
    protected   void left() {

    }

    @Override
    protected  void right() {

    }

    @Override
    protected  void space() {

    }
}
