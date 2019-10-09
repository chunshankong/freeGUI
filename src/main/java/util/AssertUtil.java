package util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class AssertUtil {

    public static final String ROOT_PATH ;

    static {
        File currentRunning = new File(AssertUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        ROOT_PATH = currentRunning.getParentFile().getAbsolutePath();
        System.err.println("running path: "+ROOT_PATH);
    }

    private static String getFullPath(String path){
        return ROOT_PATH + path;
    }

    public static BufferedImage getImage(String path){

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(getFullPath(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage getImageRegion(String path, int x,int y,int width, int height)  {
        BufferedImage image = null;
        try {
            String suffix = path.substring(path.lastIndexOf(".") + 1);

            // 取得图片读入器
            Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = (ImageReader) readers.next();

            // 取得图片读入流
            InputStream source = new FileInputStream(getFullPath(path));
            ImageInputStream iis = ImageIO.createImageInputStream(source);

            reader.setInput(iis, true);
            // 图片参数
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);

            image = reader.read(0, param);
        }catch (Exception e){
            e.printStackTrace();
        }
//        try {
//            ImageIO.write(image,"jpg",new File("d://1.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return image;
    }
    public static BufferedImage flipHorizontal(BufferedImage img) {
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate((double) -img.getWidth(), (double) 1);

        AffineTransformOp affineTransformOp = new AffineTransformOp(transform,
                null);
        return affineTransformOp.filter(img, null);
    }

    public static BufferedImage flipVertical(BufferedImage img) {
        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1);
        transform.translate((double) 1, (double) -img.getHeight());

        AffineTransformOp affineTransformOp = new AffineTransformOp(transform,
                null);
        return affineTransformOp.filter(img, null);
    }
    public static BufferedImage[][] split(BufferedImage image, int row,
                                          int column) {
        if (0 >= row || 0 >= column) {
            throw new IllegalArgumentException("非法的行或列数");
        }
        BufferedImage[][] images = new BufferedImage[row][column];
        int width = image.getWidth() / column;
        int height = image.getHeight() / row;
        int x = 0, y = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                images[i][j] = image.getSubimage(x, y, width, height);
                x += width;
            }
            x = 0;
            y += height;
        }
        return images;
    }
    public static BufferedImage [] parseOneDimensionalArray (BufferedImage [][] images){

        BufferedImage [] bufferedImages;

        int length = 0;
        // 计算一维数组长度
        for (BufferedImage[] line : images) {
            length += line.length;
        }
        // 复制元素
        bufferedImages = new BufferedImage[length];
        int index = 0;
        for (BufferedImage [] line : images) {
            for (BufferedImage column : line) {
                bufferedImages[index++] = column;
            }
        }
        return bufferedImages;
    }

    public static Properties getProperties(String path){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getFullPath(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage image = getImage("/assert/image/missile.png");
        System.out.println(image);

        Properties properties = getProperties("/assert/config/app.properties");
        System.out.println(properties.getProperty("name"));
    }
}
