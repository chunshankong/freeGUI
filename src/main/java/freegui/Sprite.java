package freegui;

import java.awt.event.KeyEvent;

public abstract class Sprite implements KeyEventListener{

    protected int x,y,width,height;

    public Sprite(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(int x,int y){
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
    }

    public Sprite(){
        this.x = 0;
        this.y = 0;
        this.width = 100;
        this.height = 100;
    }

    abstract void update();
    abstract void draw(Context context);


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

    abstract void up();
    abstract void down();
    abstract void left();
    abstract void right();

    abstract void space();

    public void keyUp(int keyCode) {

    }


}
