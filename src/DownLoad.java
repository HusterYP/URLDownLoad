import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/7/13.
 */
class DownLoad
{
    //如果这里要设计成static的话，那就与对象无关，但是后面提供的访问接口又是
    //非static的，那么要访问这些接口就需要对象，那么这里设置为static就没用咯
    private String savePath="";//文件保存路径
    private int threadCount;//线程数量
    private long totalLength;//文件总长度
    private URL url;

    //构造函数，设置各个线程共有的属性
    public DownLoad(String n,URL url,int threadCount)
    {
        savePath = n;
        this.url = url;
        this.threadCount = threadCount;
        try
        {
            URLConnection urlConnection = url.openConnection();
            totalLength = urlConnection.getContentLength();//获取文件长度
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        setSavePath();//创建保存文件
    }
    public void setSavePath()
    {
        //得出待下载的文件格式，并创建一个同样的文件存储
        String str = url.getFile();
        String num[] = str.split("\\.");
        String fileKind = "";
        if(num.length!=1)//通过获取的文件名分片得出文件类型，jpg,gif,html等
            fileKind = num[num.length-1];
        else
            fileKind = "html";
        savePath += "1."+fileKind;
    }
    //属性获取
    public File getSaveFile()
    {
        return new File(savePath);
    }
    public URL getURL()
    {
        return url;
    }
    public int getThreadCount()
    {
        return threadCount;
    }
    public long getTotalLength()
    {
        return totalLength;
    }
}
