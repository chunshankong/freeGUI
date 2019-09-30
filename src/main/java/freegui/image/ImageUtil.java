package freegui.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageUtil {

    public static void printImage(){

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(OpenCVTemplate.TEMP_PATH+"rgb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int width = img.getWidth();
        final int height = img.getHeight();

        for (int i = 0; i < width; i++) {

            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 0; j < height; j++) {

                int rgb = img.getRGB(i, j);
                stringBuffer.append(" ");
                stringBuffer.append(Integer.toHexString(rgb));

            }
            System.out.println(stringBuffer.toString());

        }
        showImg(img);

    }
    public static void showImg(BufferedImage image){

        JFrame jFrame = new JFrame(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.clearRect(0,0,800,800);
                g.drawImage(image,0,0,null);
            }
        };
        jFrame.setBounds(0,0,800,800);
        jFrame.setVisible(true);

    }

    public static void main(String[] args) throws Exception {

        printImage();

        int a  = 255;
        int r = 44;
        int g = 44;
        int b = 44;

        int argb = (a << 24)
                | (r<< 16)
                | (g << 8)
                | ( b<< 0);

        String s =Integer.toHexString(argb);

        String sc = Integer.toHexString(255)+"000000";
        int gg = new BigInteger(sc,16).intValue();
        String gs = Integer.toHexString(gg);


    }

    public static String parseWhite(String fileName,String format){

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int width = img.getWidth();
        final int height = img.getHeight();

        for (int i = 0; i < width; i++) {

            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 0; j < height; j++) {

                int rgb = img.getRGB(i, j);
                stringBuffer.append(" ");
                stringBuffer.append(Integer.toHexString(rgb));
                if (0 != rgb){
                    int whiteRGB = Color.white.getRGB();
                    img.setRGB(i, j, whiteRGB);}
                else{
//                    Color color= new Color(0, 0, 0, 0);
//                    img.setRGB(i,j,color.getRGB());
//                    String hah = Integer.toHexString(color.getRGB());
                }
            }
            System.out.println(stringBuffer.toString());

        }
        String pathName = OpenCVTemplate.TEMP_PATH+System.currentTimeMillis()+format;

        try {
            ImageIO.write(img, "png", new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathName;

//        if ("0".equals(argb)){
//            return new BigInteger(argb,16).intValue();
//        }
//
//        int a = Integer.parseInt(argb.substring(0, 2), 16);
//
//        if (a == 0){ //透明
//            return new BigInteger(argb,16).intValue();
//        }
//
//        return Color.white.getRGB();

//        int r = Integer.parseInt(argb.substring(2, 4), 16);
//        int g = Integer.parseInt(argb.substring(4, 6), 16);
//        int b = Integer.parseInt(argb.substring(6, 8), 16);


    }

    private static int grayRGB(String argb) {
        //ARGB前两位是透明度,后面开始是RGB
        int r = Integer.parseInt(argb.substring(2, 4), 16);
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        //平均值
        String average = Integer.toHexString((r + g + b) / 3);

        if (average.length() == 1) {
            average = "0" + average;
        }
        //RGB都变成平均值
        return Integer.parseInt(average + average + average, 16);
    }

}
