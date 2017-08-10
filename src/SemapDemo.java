import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by 袁刚 on 2017/8/1.
 */
public class SemapDemo implements Runnable {
    final Semaphore semaphore= new Semaphore(5);

    @Override
    public void run() {
        try{
            semaphore.acquire();
            //模拟耗时操作
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+":done");
            semaphore.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemapDemo semapDemo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            executorService.submit(semapDemo);
        }
    }
}
