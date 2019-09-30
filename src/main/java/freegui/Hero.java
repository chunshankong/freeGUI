package freegui;

import java.awt.*;

public class Hero extends Sprite {

    void update() {

    }

    void draw(Context context) {
        context.setColor(Color.RED);
        context.drawRect(x,y,width,height);
    }

    void up() {
        this.y -= 10;
    }

    void down() {
        this.y +=10;
    }

    void left() {
        x -= 10;
    }

    void right() {
        x += 10;
    }

    @Override
    void space() {
        Window.addSprite(new Wave(x,y,10,10));
    }

}
