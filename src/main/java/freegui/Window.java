package freegui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Window {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private int x,y,width,height;

    private JFrame targetFrame;
    private static Window self;
    private BufferedImage image;
    private Context context;

    private MyArrayList<Sprite> sprites;
    private MyArrayList<KeyEventListener> keyEventListeners;

    private Window(){}

    public static void createWindow( int x,  int y,  int width,  int height){

        if (self == null){
            self = new Window();
        }
        self.x = x;
        self.y = y;
        self.width = width;
        self.height = height;

        self.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        self.context = new Context(self.image.createGraphics());
        self.sprites = new MyArrayList<>();
        self.keyEventListeners = new MyArrayList<>();

        self.targetFrame = new JFrame("free gui window");
        self.targetFrame.setBounds(x,y,width,height);
        self.targetFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        self.targetFrame.setContentPane(new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(self.image,0,0,null);
            }
        });
        self.targetFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                for (KeyEventListener listener : self.keyEventListeners) {
                    listener.keyDown(e.getKeyCode());
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                for (KeyEventListener listener : self.keyEventListeners) {
                    listener.keyUp(e.getKeyCode());
                }
            }
        });

        self.targetFrame.setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    self.loop();
                }
            }

        }).start();

    }
    public static void createWindow(int x,int y){
        createWindow(x,y,WIDTH,HEIGHT);
    }

    public static void addSprite(Sprite sprite){
        self.sprites.add(sprite);
        self.keyEventListeners.add(sprite);
    }

    private void update(){
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }
    private void render(){

        context.clearRect(0,0,width,height);

        for (Sprite sprite : sprites) {
            sprite.draw(self.context);
        }
        self.targetFrame.repaint();

    }

    private void loop(){

        update();
        render();
    }



}
