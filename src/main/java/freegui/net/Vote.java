package freegui.net;

import freegui.image.ImageUtil;
import freegui.image.OpenCVTemplate;
import org.opencv.core.Point;
import sun.misc.IOUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vote {


    private static String proxyHost = "127.0.0.1";
    private static int proxyPort = 1080;
    private static String proxyUser = "user";
    private static String proxyPass = "pass";

    public static void main(String[] args) throws UnsupportedEncodingException {


//        System.out.println(decode("\\u9700\\u8981\\u9a8c\\u8bc1\\u7801"));
        System.out.println(decode("\\u9a8c\\u8bc1\\u5931\\u8d25\\uff0c\\u8bf7\\u91cd\\u8bd5\\uff01"));
        System.out.println("begin:"+new Date().toLocaleString());
        vote();
        System.out.println("end:"+new Date().toLocaleString());

//        String s = "{\"result\":0,\"data\":{\"$wxOpenId\":\"\",\"name\":\"\\u5510\\u6653\\u4e3d\",\"votes\":243,\"rank\":7,\"groupRank\":\"-\"},\"msg\":\"ok\"}\n";
//        resolveName(s);

    }

    public static void vote(){
        int count = 20;
        int id = 322312;
        String verifyCode = "";
        for (int i = 0; i < count; i++) {
            sendRequest(id,verifyCode);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getVerifyCode(){

        String url = "https://res2.rrxiu.net/sms/VerifyActiveImage?" +
                "wsiteGuid=4zkszy&guid=c4c202c05bcbc57ef824c2a7f3923202322312&wxOpenId=5479985521309554298532286&capType=shape";
        String result = get(url);

        String sourceUrl = "";
        String regex = "(?<=(\"" + "bg" + "\":\")).*?(?=(\"))";
        Pattern mPAvatar = Pattern.compile(regex);
        Matcher mMAvatar = mPAvatar.matcher(result);
        while (mMAvatar.find()) {
            sourceUrl = mMAvatar.group();
            sourceUrl = sourceUrl.replaceAll("\\\\","");
        }

        String templateUrl = "";
        String regex2 = "(?<=(\"" + "front" + "\":\")).*?(?=(\"))";
        Pattern mPAvatar2 = Pattern.compile(regex2);
        Matcher mMAvatar2 = mPAvatar2.matcher(result);
        while (mMAvatar2.find()) {
            templateUrl = mMAvatar2.group();
            templateUrl = templateUrl.replaceAll("\\\\","");
        }

        String source = OpenCVTemplate.download(sourceUrl,sourceUrl.substring(sourceUrl.lastIndexOf(".")));
        String template = OpenCVTemplate.download(templateUrl,templateUrl.substring(templateUrl.lastIndexOf(".")));

        //取反色 template
        template = ImageUtil.parseWhite(template,".png");

        Point point = OpenCVTemplate.standard(source,template,false);//39.375:95.0625
        String x = String.valueOf(point.x );
        String y = String .valueOf(point.y);
        x = x.substring(0,x.indexOf("."));
        y = y.substring(0,y.indexOf("."));

        String verifyCode = x+".375"+":"+y+".0625";
        System.out.println(verifyCode);
        return verifyCode;
    }
    public static String get(String urlStr)
    {
        String result = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection(); // 打开连接

            System.out.println(urlConnection.getURL().toString());

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            br.close();
              result = sb.toString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void resolveName(String text){
        String result = "";
        String regex = "(?<=(\"" + "name" + "\":\")).*?(?=(\"))";
        Pattern mPAvatar = Pattern.compile(regex);
        Matcher mMAvatar = mPAvatar.matcher(text);
        while (mMAvatar.find()) {// 如果找到 开始替换
            result = mMAvatar.group();
        }

        System.err.println(result);

        String r = decode(result);
        System.err.println(r);
    }

    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 匹配以("fieldName":")开头,以(")结尾的字符串，并且存储到分组中
     * 正则表达式匹配字段值
     * 不包含空值
     * (?<=(href=")) 表示 匹配以(href=")开头的字符串，并且捕获(存储)到分组中
     * (?=(">)) 表示 匹配以(">)结尾的字符串，并且捕获(存储)到分组中
     *
     * @param jsonStr
     * @param fieldName
     * @return
     */
    public static List<String> getFieldListFromJsonStr(String jsonStr, String fieldName) {
        List<String> fieldValues = new ArrayList<>();
        String regex = "(?<=(\"" + fieldName + "\":\")).*?(?=(\"))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonStr);
        while (matcher.find()) {
//            if (StringUtils.isNotEmpty(matcher.group().trim())) {
//                fieldValues.add(matcher.group().trim());
//            }
        }
        return fieldValues;
    }

    public static void sendRequest(int id,String verifyCode){
        String vurl = "https://res2.rrxiu.net/pluginPhotoVote/photoVoteData2/vote";
        String my = "http://47.107.146.2/";
        String gurl ="https://www.google.com/?gws_rd=ssl";
        doProxy();
//        String content = request(gurl);
        String content = doPost(vurl,"wsiteGuid=4zkszy&identify=5479985521309554298532286" +
                "&wxOpenId=o-BDIt6p7x_rLdmypol9k3_RR6Scnicedrokjgf8w&nickName=人人秀&headimgurl=https://assets.rrxh5.cc/www/images/no_head.jpg" +
                "&guid=c4c202c05bcbc57ef824c2a7f3923202&groupId=0&id="+id+"&fg=jd8gj92ldojf0976jjd72jdhslldjk68"+"&verifyCode="+verifyCode);
        try {
            content = new String(content.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("Result :===================\n " + content);
        resolveName(content);
    }
    /**
     * 通过系统变量方式实现代理
     *
     * @return
     */
    public static void doProxy() {
        // 设置系统变量

        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", "" + proxyPort);
        // 针对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", "" + proxyPort);
        // 设置默认校验器
        setDefaultAuthentication();

    }

    public static String request(String url){
        //开始请求
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();

//            HttpsURLConnection httpsCon = (HttpsURLConnection) conn;
//            httpsCon.setFollowRedirects(true);

            String encoding = conn.getContentEncoding();
            if (encoding == null || "".equals(encoding)) {
                encoding = "UTF-8";
            }
            InputStream is = conn.getInputStream();
            String content = new String(IOUtils.readFully(is,-1,true),encoding);
//            String content = IOUtils.toString(is, encoding);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String doPost(String url,String parameterData )  {

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {

            URL localURL = new URL(url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > Chrome/37.0.0.0 Mobile Safari/537.36 V1_AND_SQ_5.3.1_196_YYB_D > QQ/5.3.1.2335 NetType/WIFI");

            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(parameterData.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if (reader != null) {
                    reader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return resultBuffer.toString();
    }

    /**
     * 设置全局校验器对象
     */
    public static void setDefaultAuthentication() {
        BasicAuthenticator auth = new BasicAuthenticator("","");
        Authenticator.setDefault(auth);
    }
    /**
     * 实现sun.net的代理验证
     *
     * @author tzz
     * @createDate 2015年7月23日
     *
     */
    public static class BasicAuthenticator extends Authenticator {
        String userName;
        String password;
        public BasicAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
        /**
         * Called when password authorization is needed. Subclasses should override the default implementation, which returns null.
         *
         * @return The PasswordAuthentication collected from the user, or null if none is provided.
         */
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            //System.out.println("DEBUG === use global authentication of password");
            return new PasswordAuthentication(userName, password.toCharArray());
        }
    }
}
