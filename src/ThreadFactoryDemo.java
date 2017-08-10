import java.util.concurrent.*;

/**
 * Created by 袁刚 on 2017/8/3.
 */

/**
 * 自定义线程创建：ThreadFactory
 */
public class ThreadFactoryDemo {

    public static class Mytask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread ID:"+Thread.currentThread().getId());
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Mytask mytask = new Mytask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                System.out.println("create "+t);
                return t;
            }
        });
        for (int i = 0; i <5 ; i++) {
            es.submit(mytask);
        }
        Thread.sleep(2000);
    }

}
