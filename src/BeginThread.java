import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//这个类是根据传入的线程数量来开启对应数量的线程
class BeginThread
{
    private DownLoad downLoad;
    private File file;

    public BeginThread(DownLoad d)
    {
        downLoad = d;
        file = d.getSaveFile();//返回保存的文件引用
    }
    //开启线程
    public void BeginRun() throws ExecutionException, InterruptedException, IOException
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        Vector<Future<Vector<Byte>>> sumByte = new Vector<Future<Vector<Byte>>>();
        for(int i=0;i<downLoad.getThreadCount();i++)
        {
            sumByte.add(exec.submit(new ThreadRun(downLoad,i+1)));
        }
        FileOutputStream of = new FileOutputStream(file);
        for(Future<Vector<Byte>> bytes:sumByte)
        {
            System.out.println(bytes.get().capacity());
            for(int i=0;i<bytes.get().size();i++)
            {
                of.write(bytes.get().get(i));
            }
        }
    }
}