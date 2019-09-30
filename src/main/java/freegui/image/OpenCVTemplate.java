package freegui.image;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

public class OpenCVTemplate {

    public static final String TEMP_PATH = "D://temp/img/";

    /**
     * BufferedImage转换成Mat
     *
     * @param original
     *            要转换的BufferedImage
     * @param imgType
     *            bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType
     *            转换成mat的type 如 CvType.CV_8UC3
     */
    public static Mat BufImg2Mat (BufferedImage original, int imgType, int matType) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        BufferedImage image = null;
        // Don't convert if it already has correct type
        if (original.getType() != imgType) {

            // Create a buffered image
              image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);

            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }else {
            image = original;
        }

        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(image.getHeight(), image.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }
    private static Mat bufferedImageToMat(BufferedImage inBuffImg)
    {
        BufferedImage image = new BufferedImage(inBuffImg.getWidth(), inBuffImg.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d= image.createGraphics();
        g2d.drawImage(inBuffImg, 0, 0, null);
        g2d.dispose();

        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8U);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);

        return mat;
    }
    public static void custom(){
        {

//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            OpenCV.loadShared();
            System.out.printf("java.library.path: %s%n",
                    System.getProperty("java.library.path"));

            Mat source, template;

            File file1 = new File("C:/Users/asus/Desktop/source.png");//本地图片
            File file2 = new File("C:\\Users\\asus\\Desktop\\template.jpg");//本地图片

            BufferedImage bs = null;
            BufferedImage tp = null;
            try {
                bs=(BufferedImage) ImageIO.read(file1);
                tp=(BufferedImage) ImageIO.read(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
//source = bufferedImageToMat(bs);
            source = BufImg2Mat(bs,BufferedImage.TYPE_4BYTE_ABGR,CvType.CV_8U);
            template= BufImg2Mat(tp,BufferedImage.TYPE_4BYTE_ABGR,CvType.CV_8U);

            //将文件读入为OpenCV的Mat格式
//        source = Highgui.imread("C:\\Users\\asus\\Desktop\\soure.png");
//        template = Highgui.imread("C:\\Users\\asus\\Desktop\\template.jpg");
            //创建于原图相同的大小，储存匹配度
            Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1, CvType.CV_32FC1);
            //调用模板匹配方法
            Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
            //规格化
            Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
            //获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
            Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
            Point matchLoc = mlr.minLoc;
            //在原图上的对应模板可能位置画一个绿色矩形
//        Core.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()), new Scalar(0, 255, 0));
            //将结果输出到对应位置
//        Highgui.imwrite("C:\\cv.jpg", Mat2BufImg(source,".jpg"));
            try {
                ImageIO.write(Mat2BufImg(source,".jpg"), "jpg", new File("D:\\cv.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
//        Imgproc.rectangle();
        }
    }

    public static void main(String[] args){
//        Point point = standard("C:/Users/asus/Desktop/source.png","C:\\Users\\asus\\Desktop\\template.jpg");



        String sourcePath = "C:/Users/asus/Desktop/source.jpg";
        String templatePath = "C:/Users/asus/Desktop/template.png";
        Point point = standard(sourcePath,templatePath,false);
        System.out.println(point.x);
        System.out.println(point.y);

//        outline(sourcePath,sourcePath.substring(sourcePath.lastIndexOf(".")));
//        outline(templatePath,templatePath.substring(templatePath.lastIndexOf(".")));

//        canny(sourcePath,sourcePath.substring(sourcePath.lastIndexOf(".")));
//        canny(templatePath,templatePath.substring(templatePath.lastIndexOf(".")));
//        wise(templatePath,".png");

        //        String fileName = download("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570196033&di=bcdab4f304a7fdbdfe306a3437bd0adb&imgtype=jpg&er=1&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F2237f029eff118329917bdee8af4b7c4a568e3834c600-6EpVYp_fw658",1);
//        System.out.println(fileName);
//
//        String temp = "D:/temp/img/6.jpg";
//
//                Point point = standard(fileName,temp);
//        System.out.println(point.x);
//        System.out.println(point.y);

    }

    /**
     * OpenCV-4.0.0 边缘检测
     * @return: void
     * @date: 2019年5月7日12:16:55
     */
    public static void canny(String filename,String format) {
        Mat src = Imgcodecs.imread(filename);
        Mat dst= new Mat();
        Mat gray= new Mat();
        Mat image= new Mat();
        //1 高斯降噪
        Imgproc.GaussianBlur(src, dst, new Size(3,3),5,5);
        //2 转灰度图片
        Imgproc.cvtColor(dst, gray, Imgproc.COLOR_BGR2GRAY);
        //3 描绘边缘
        Imgproc.Canny(gray, image, 3, 6, 3, false);
        Mat colorimage=new Mat(image.size(),image.type());
        src.copyTo(colorimage, image);

        Imgcodecs.imwrite(TEMP_PATH+System.currentTimeMillis()+format, image);

    }

    //背景去除
    private static Mat doBackgroundRemoval(String filename,String format)
    {
        Mat frame  = Imgcodecs.imread(filename);

        // init
        Mat hsvImg = new Mat();
        java.util.List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();

        int thresh_type = Imgproc.THRESH_BINARY_INV;

        // threshold the image with the average hue value
        hsvImg.create(frame.size(), CvType.CV_8U);
        Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);
        Core.split(hsvImg, hsvPlanes);

        // get the average hue value of the image

        Scalar average=Core.mean(hsvPlanes.get(0));
        double threshValue =average.val[0];
        Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, thresh_type);

        Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));

        // dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);

        Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY);

        // create the new image
        Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        thresholdImg.convertTo(thresholdImg,CvType.CV_8U);
        frame.copyTo(foreground, thresholdImg);//掩膜图像复制

        Imgcodecs.imwrite(TEMP_PATH+System.currentTimeMillis()+format, foreground);
        return foreground;
    }

    public static String wise(String filename,String format){

        OpenCV.loadShared();

        Mat src = Imgcodecs.imread(filename);
        Mat dst = src.clone();

        Core.bitwise_not(src,dst);

        String path = TEMP_PATH+System.currentTimeMillis()+format;
        Imgcodecs.imwrite(path, dst);
        return path;
    }

    //轮廓检测
    public static void outline(String filename,String format){
        OpenCV.loadShared();
        Mat src = Imgcodecs.imread(filename);
        Mat dst = src.clone();
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.adaptiveThreshold(dst, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY_INV, 3, 3);

        java.util.List<MatOfPoint> contours = new java.util.ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE,
                new Point(0, 0));
        System.out.println(contours.size());
        for (int i = 0; i < contours.size(); i++)
        {
            Imgproc.drawContours(src, contours, i, new Scalar(0, 0, 0, 0), 1);
        }

        Imgcodecs.imwrite(TEMP_PATH+System.currentTimeMillis()+format, src);
    }

    public static String download(String urlString,String format)  {
        String filename = null;
        try {

            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
              filename = "D:/temp/img/" + System.currentTimeMillis() + format;  //下载路径及下载图片名称
            File file = new File(filename);
            FileOutputStream os = new FileOutputStream(file, true);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return filename;
    }

    public static Point standard(String sourcePath,String templatePath,boolean gray){
        OpenCV.loadShared();
        Mat source, template;
        //将文件读入为OpenCV的Mat格式
        if (gray){
            source = Imgcodecs.imread(sourcePath,CV_LOAD_IMAGE_GRAYSCALE);
            template = Imgcodecs.imread(templatePath,CV_LOAD_IMAGE_GRAYSCALE);
        }else {
            source = Imgcodecs.imread(sourcePath);
            template = Imgcodecs.imread(templatePath);
        }

        //创建于原图相同的大小，储存匹配度
        Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1, CvType.CV_32FC1);
        //调用模板匹配方法
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
        //规格化
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        //获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        Point matchLoc = mlr.minLoc;

        //在原图上的对应模板可能位置画一个绿色矩形
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()), new Scalar(0, 255, 0));

        Point clickLoc = new Point(matchLoc.x,matchLoc.y);
        clickLoc.x += template.width()/2;
        clickLoc.y += template.height()/2;
        Imgproc.circle(source, clickLoc,20,new Scalar(0, 255, 0));

        //将结果输出到对应位置
        Imgcodecs.imwrite("D:/result.jpeg", source);
        return clickLoc;
    }


    /**
     * Mat转换成BufferedImage
     *
     * @param matrix
     *            要转换的Mat
     * @param fileExtension
     *            格式为 ".jpg", ".png", etc
     * @return
     */
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) {
        // convert the matrix into a matrix of bytes appropriate for
        // this file extension
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }
}