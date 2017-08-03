import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Main
{
    public static void main(String[]args) throws ExecutionException, InterruptedException, IOException
    {
//        String webAddress = "http://39.108.79.219/";
        String webAddress = "http://t2.27270.com/uploads/tu/201707/9999/44017f1fbb.jpg";
//        String webAddress = "http://img.boqiicdn.com/Data/Bbs/Users/135/13516/1351667/img32061381071677.gif";
//        String webAddress = "https://www.v2ex.com/api/topics/hot.json";
        String savePath = "F:"+ File.separator+"Java"+File.separator;
        int threadCount = 3;//线程数量
        URL url = new URL(webAddress);
        DownLoad downLoad = new DownLoad(savePath,url,threadCount);
        BeginThread beginThread = new BeginThread(downLoad);
        beginThread.BeginRun();

        System.out.println("Main Thread is dead");
    }
}