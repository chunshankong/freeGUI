package freegui;

public class Wave extends Sprite {
    public Wave(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    @Override
    void update() {
        x+=1;
    }

    @Override
    void draw(Context context) {
        context.drawOval(x,y,width,height,true);
    }

    @Override
    void up() {

    }

    @Override
    void down() {

    }

    @Override
    void left() {

    }

    @Override
    void right() {

    }

    @Override
    void space() {

    }
}
