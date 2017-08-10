/**
 * Created by 袁刚 on 2017/8/3.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定大小的线程池
 */

/**
 * 创建了固定大小的线程池
 * 依次向线程池提交10个任务
 * 线程池会安排这十个任务
 */
public class TreadPoolDemo {
    public static class Mytask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread ID:"+Thread.currentThread().getId());
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Mytask mytask = new Mytask();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i <10 ; i++) {
            es.submit(mytask);
        }
    }
}
