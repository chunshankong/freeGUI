package freegui;

public class Test {
    public static void main(String[] args) {

        System.out.println("test");

        Window.createWindow(100,100);
        Hero hero = new Hero();
        Window.addSprite(hero);


    }
}
