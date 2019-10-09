package freegui;

import freegui.plane.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import static freegui.plane.Hero.count;

public class Window {
    public static int WIDTH = 500;
    public static int HEIGHT = 500;

    private static int x,y = 0;

    private JFrame targetFrame;
    private static Window self;
    private BufferedImage image;
    private Context context;
    private static boolean highQuality = true;

    public static boolean isHighQuality() {
        return highQuality;
    }
    public static void setHighQuality(boolean highQuality) {
        Window.highQuality = highQuality;
    }

    private static CopyOnWriteArrayList<Sprite> sprites;
    private CopyOnWriteArrayList<KeyEventListener> keyEventListeners;

    private CopyOnWriteArrayList<Task> tasks;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Window(){}

    public static void createWindow( int x,  int y,  int width,  int height){

        if (self == null){
            self = new Window();
        }
        self.x = x;
        self.y = y;
        self.WIDTH = width;
        self.HEIGHT = height;

        self.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        self.context = new Context(self.image.createGraphics());
        self.sprites = new CopyOnWriteArrayList<>();
        self.keyEventListeners = new CopyOnWriteArrayList<>();
        self.tasks = new CopyOnWriteArrayList<>();

        self.targetFrame = new JFrame("free gui window");
        self.targetFrame.setBounds(x,y,width,height);
        self.targetFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        self.targetFrame.setContentPane(new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(self.image,0,0,null);
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

        System.err.println(width +" * "+ height);


    }
    public static void createWindow(int x,int y){
        createWindow(x,y,WIDTH,HEIGHT);
    }
    public static void createWindow(){
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        createWindow(x,y,screensize.width,screensize.height);
        self.targetFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void addSprite(Sprite sprite){
        self.sprites.add(sprite);
        self.keyEventListeners.add(sprite);
    }

    public static void removeSprite(Sprite sprite){
        self.sprites.remove(sprite);
        self.keyEventListeners.remove(sprite);
        if (sprite instanceof freegui.plane.Wave){
            System.out.println("-"+Hero.count);
            Hero.count--;
        }
    }

    public static void addTask(Long millis, Function function){
        self.tasks.add(new Task(millis,function));
    }

    public static void setTitle(String title){
        self.targetFrame.setTitle(title);
    }
    public static void showMsg(String msg){
        JOptionPane.showMessageDialog(self.targetFrame, msg, "消息", JOptionPane.PLAIN_MESSAGE);
    }

//    public static boolean show = true;
//   static void log(Rectangle r1,Rectangle r2){
//        if (show){
//        }
//    }

//    public static ArrayList<Sprite> getSprites(){
//       return sprites;
//    }

    public static List<Sprite> getCollisionObjects(Sprite sprite){

        List<Sprite> collisionObjects = new ArrayList<>();

        for (Iterator<Sprite> ite = sprites.iterator(); ite.hasNext();) {
            Sprite obj = ite.next();
            if (obj == sprite){
                continue;
            }
            if (obj.getBounds().intersects(sprite.getBounds())){
                collisionObjects.add(obj);
            }
        }
        return collisionObjects;
    }

    public static <T> T getCollisionWithClass(Sprite sprite,Class<T> cls) {

        T collisionObject = null;
        List<Sprite> collisionObjects = getCollisionObjects(sprite);

        for (Iterator<Sprite> ite = collisionObjects.iterator(); ite.hasNext();) {
            Sprite obj = ite.next();
            if (obj.getClass().equals(cls)){
                collisionObject = (T) obj;
            }
        }
        return collisionObject;
    }


    private void update(){

        for (Iterator<Sprite> ite = sprites.iterator(); ite.hasNext();) {
            Sprite sprite = ite.next();
            sprite.update();
        }
    }
    private void executeTask(){

        for (Iterator<Task> ite = tasks.iterator(); ite.hasNext();) {
            Task task = ite.next();
            if (task.execute()){
                tasks.remove(task);
            }
        }

    }
    private void render(){

        context.clearRect(0,0,WIDTH,HEIGHT);

        for (Iterator<Sprite> ite = sprites.iterator(); ite.hasNext();) {
            Sprite sprite = ite.next();
            sprite.draw(self.context);
        }

        self.targetFrame.repaint();
    }

    private void loop(){

        update();
        executeTask();
        render();
    }



}
