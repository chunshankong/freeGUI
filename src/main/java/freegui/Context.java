package freegui;

import java.awt.*;

public class Context {

    private Graphics2D targetGraphics;

    public Context(Graphics2D graphics2D){
        targetGraphics = graphics2D;
        targetGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void setColor(Color color){
        targetGraphics.setColor(color);
    }

    public void drawRect(int x,int y,int width,int height)
    {
        targetGraphics.drawRect(x,y,width,height);
    }

    public void clearRect(int x,int y,int width,int height){
        targetGraphics.clearRect(x,y,width,height);
    }
    public void drawOval(int x,int y,int width,int height,boolean fill)
    {
        if (fill){
            targetGraphics.fillOval(x,y,width,height);
        }else {
            targetGraphics.drawOval(x,y,width,height);
        }
    }


}
