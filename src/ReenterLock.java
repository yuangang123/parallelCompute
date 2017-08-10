/**
 * Created by 袁刚 on 2017/8/1.
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * 可以使用java.unti.concurrent.locks.Reenterlock类来实习
 */
public class ReenterLock implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    @Override
    public void run() {
        for (int j = 0; j <100000000 ; j++) {
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock t1= new ReenterLock();
        Thread thread =new Thread(t1);
        Thread thread2 = new Thread(t1);
        long time1 = System.currentTimeMillis();
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();
        long time2 = System.currentTimeMillis();

        System.out.println("并行计算的结果："+i+"使用时间："+(time2-time1));

        time1 = System.currentTimeMillis();
        for (int j = 0; j <100000000 ; j++) {
            j++;

        }
        System.out.println(System.currentTimeMillis()-time1);
    }

}
