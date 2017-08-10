import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 袁刚 on 2017/8/3.
 */
public class ExtThreadPool {
    public static class Mytask implements Runnable{
        public String name;
        public Mytask(String name){
            this.name =name;
        }

        @Override
        public void run() {
            System.out.println("正在执行"+":Thread ID:"+Thread.currentThread().getId()+", Task Name="+name);
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                System.out.println("准备执行："+(((Mytask)r).name));
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println("执行完成："+(((Mytask)r).name));
            }

            @Override
            protected void terminated() {
                super.terminated();
                System.out.println("线程池退出");
            }
        };

        for (int i = 0; i <5 ; i++) {
            Mytask mytask= new Mytask("TASK_GEYM_"+i);
            es.execute(mytask);
            Thread.sleep(10);
        }
        es.shutdown();
    }
}
