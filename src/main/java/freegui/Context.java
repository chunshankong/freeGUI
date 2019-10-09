package freegui;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private Graphics2D targetGraphics;

    private static Map<BufferedImage,Image> scaleCache;

    public Context(Graphics2D graphics2D){
        targetGraphics = graphics2D;
        //消除文字锯齿
        targetGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //消除画图锯齿
        targetGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        scaleCache = new HashMap<>();
    }
    public void setColor(Color color){
        targetGraphics.setColor(color);
    }

    public void drawRect(int x,int y,int width,int height,boolean fill)
    {
        if (fill){
            targetGraphics.fillRect(x,y,width,height);
        }else {
            targetGraphics.drawRect(x,y,width,height);
        }
    }

    public void clearRect(int x,int y,int width,int height){
        targetGraphics.clearRect(x,y,width,height);
    }
    public void drawOval(int x,int y,int width,int height,boolean fill) {
        if (fill){
            targetGraphics.fillOval(x,y,width,height);
        }else {
            targetGraphics.drawOval(x,y,width,height);
        }
    }
    public void drawImage(BufferedImage image,int x, int y){
        targetGraphics.drawImage(image,x,y,null);
    }

    public void drawImage(BufferedImage image,int x,int y,int width,int height){
        if (Window.isHighQuality()){
            Image scaleImg = scaleCache.get(image);
            if (scaleImg == null){
                scaleImg = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
                scaleCache.put(image,scaleImg);
            }
//            BufferedImage newImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY,
//                    width,height);//
            targetGraphics.drawImage(scaleImg,x,y,null);
        }else {
            targetGraphics.drawImage(image,x,y,width,height,null);
        }
    }
    public void drawString(String str,int x,int y,int size){
        Font font = new Font(Font.SERIF,Font.BOLD,size);
        targetGraphics.setFont(font);  //字体高度
        targetGraphics.drawString(str,x,y);
    }

}
