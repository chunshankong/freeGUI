package freegui.step1;

import java.io.IOException;

public class SystemCMD
{

    public static void main(String[] args) {
        shutdown();
    }
    public static void shutdown()
    {

       /* System.out.print("ÇëÊäÈë¶àÉÙ·ÖÖÓºó¹Ø»ú:");
        Scanner scanner = new Scanner(System.in);
        int minute = scanner.nextInt()*60;*/
        Runtime runtime = Runtime.getRuntime();
//        String shutdown = "shutdown -s -t "+minute;
        String shutdown = "shutdown -s -t 0";
        try {
            runtime.exec(shutdown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
