import java.util.concurrent.locks.LockSupport;

/**
 * Created by 袁刚 on 2017/8/3.
 */

/**
 * 本程序演示的是关于LockSupport的使用方法
 * 是非常方便实用的线程阻塞工具，可以在线程内的任意地方阻塞
 * LockSupport使用了类似信号量的机制，它为每一个线程准备了一个许可
 * 若许可可用，则park()函数会立即返回，斌且消费这个许可，如果许可不可以使用，就会阻塞
 * unpark会使一个许可变为可用
 *
 * 所以不会出现类似于Thread.suspend和Thread.resume那样的情况
 *
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");



    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("In "+getName());
                LockSupport.park();
            }

        }
    }

    public static void main(String[] args)throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

}
