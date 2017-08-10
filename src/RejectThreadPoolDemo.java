import java.util.concurrent.*;

/**
 * Created by 袁刚 on 2017/8/3.
 */

/**
 * ThreadPoolExecutor最后一个参数指定了拒绝策略
 */
public class RejectThreadPoolDemo {
    public static class Mytask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+": Thread ID:"+Thread.currentThread().getId());
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Mytask mytask= new Mytask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(10), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString()+"is discard");
            }
        });
        for (int i = 0; i <Integer.MAX_VALUE ; i++) {
            es.submit(mytask);
            Thread.sleep(10);
        }
    }
}
