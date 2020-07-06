import com.itheima.*;

import java.io.File;
import java.util.concurrent.*;

public class Main {
    public static final int READ_THREAD_COUNT = 5;
    public static final int SORT_THREAD_COUNT = 5;

    public static void main(String[] args) {
        String FilePath = System.getProperty("user.dir")+"\\data";
        File[] files = new File(FilePath).listFiles();

        SortResources sortResources = new RiseMinQuotaSortResource(files.length);

        ExecutorService executor = Executors.newFixedThreadPool(READ_THREAD_COUNT + SORT_THREAD_COUNT);
        //讲解：为什么用CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(SORT_THREAD_COUNT);
        for(int i = 0;i < READ_THREAD_COUNT;i++){
            ReadFileThread readFileThread = new ReadFileThread(sortResources);
            for(int j = 0;j < files.length ;j++){
                if((j+1) % READ_THREAD_COUNT == (i+1)){
                    readFileThread.addFile(files[j]);
                }
            }
            executor.execute(readFileThread);
        }

        for(int i = 0;i < SORT_THREAD_COUNT;i++){
            executor.execute(new SortThread(sortResources,countDownLatch));
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------结果展示----------------");
        for(DataVo vo:sortResources.getSortResult()){
            System.out.println(vo);
        }
        executor.shutdown();
    }
}
