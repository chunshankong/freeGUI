package freegui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Function;

public abstract class Sprite implements KeyEventListener{

    protected int x,y,width,height;

    protected boolean live = true;

    public Sprite(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isLive() {
        return live;
    }
    protected void setLive(boolean live){
        this.live = live;
    }

    public void endLive(){
        if (!isLive()){
            return;
        }
        setLive(false);
        afterEndLive();
    }
    protected abstract void afterEndLive();

    protected void dispose(){
        Window.removeSprite(this);
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    protected abstract void update();
    protected abstract void draw(Context context);


    public void keyDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT: {
                left();
            }
            break;
            case KeyEvent.VK_RIGHT: {
                right();
            }
            break;
            case KeyEvent.VK_UP: {
                up();
            }
            break;
            case KeyEvent.VK_DOWN: {
                down();
            }
            break;
            case KeyEvent.VK_SPACE: {
                space();
            }
            break;
        }
    }

    protected abstract void up();
    protected abstract void down();
    protected abstract void left();
    protected abstract void right();

    protected abstract void space();

    public void keyUp(int keyCode) {

    }



}
