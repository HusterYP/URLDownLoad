import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.concurrent.Callable;

//这个类主要是针对单个线程的，单个线程应该做什么
class ThreadRun  implements Callable
{
    private DownLoad downLoad;
    private Vector<Byte> bytes = new Vector<>();//存储字节,以字节写入
    private long perSum;//每个线程应该下载的字节长度,线程相关
    private long skipCount;//应该跳过的字节长度，线程相关

    public ThreadRun(DownLoad d,int i)//i表示第几条线程
    {
        downLoad = d;
        if(i==d.getThreadCount())//如果是最后一条线程
        {
            //最后一条线程的总下载量应该是文件总长度-其他线程的下载量
            perSum = d.getTotalLength() - (d.getTotalLength()/d.getThreadCount())*(i-1)+2000;
            //最后一条线程应该跳过的字节长度
            skipCount = (d.getTotalLength()/d.getThreadCount())*(i-1);
            System.out.println(perSum+"  "+skipCount);
        }
        else
        {
            //如果不是最后一条线程，每条线程的下载量应该是 总文件长度/线程数量
            perSum = d.getTotalLength() / d.getThreadCount();
            //跳过字节长度
            skipCount = (i - 1) * perSum;
            System.out.println(perSum+"  "+skipCount);
        }
    }
    //通过获取的单个线程的下载量和跳过的字节数，下载对应线程的字节，线程相关
    public Vector<Byte> call()
    {
        System.out.println(Thread.currentThread().getName());
        InputStream in;
        long count = 0;//计数，已经接受了多少字节
        try
        {
            in = downLoad.getURL().openStream();
            in.skip(skipCount);

            byte b[] = new byte[1];
            while ((in.read(b)) != -1&&count<perSum)//如果没有达到文件末尾，而且对应线程的下载量没有完成
            {
                count++;
                bytes.add(b[0]);
                if(count%20==0)
                    Thread.yield();
            }
            in.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }
}
