/**
 * Created by 袁刚 on 2017/8/1.
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的限时等待
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try{
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
                System.out.println(Thread.currentThread().getId()+":成功完成");
            }
            else{
                System.out.println(Thread.currentThread().getId()+": get lock failed");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TimeLock lock= new TimeLock();
        Thread t1 =new Thread(lock);
        Thread t2 = new Thread(lock);
        t1.start();
        t2.start();
    }
}
