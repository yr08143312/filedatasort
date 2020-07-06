import com.itheima.*;

import java.io.File;
import java.util.concurrent.*;

public class Main {
    public static final int READ_THREAD_COUNT = 10;

    public static void main(String[] args) {
        String FilePath = System.getProperty("user.dir")+"\\data";
        File[] files = new File(FilePath).listFiles();
        SortInterface sort = new RiseGroupMinquotaSort();
        SortResources sortResources = new SortResources(files.length,sort);

        ExecutorService executor = Executors.newFixedThreadPool(READ_THREAD_COUNT);

        for(int i = 0;i < READ_THREAD_COUNT;i++){
            ReadFileThread readFileThread = new ReadFileThread(sortResources);
            for(int j = 0;j < files.length ;j++){
                //文件号码和线程数量取模
                int mod = (j+1) % READ_THREAD_COUNT;
                //如果取模刚好等于线程编号
                //或者
                //取模等于零，并且刚好线程是最后一个线程
                //需要理解下
                if(mod == (i+1) || (mod == 0 && (i+1) == READ_THREAD_COUNT)){
                    readFileThread.addFile(files[j]);
                }
            }
            executor.execute(readFileThread);
        }

        Thread sortThread = new Thread(new SortThread(sortResources));
        sortThread.start();
        try {
            sortThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //结果打印
        System.out.println("---------------结果打印---------------");
        for(DataVo vo:sort.getResult()){
            System.out.println(vo);
        }

        executor.shutdown();
    }
}
