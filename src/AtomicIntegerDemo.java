import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 袁刚 on 2017/8/5.
 */

/**
 * 简单使用AtomicInteger
 * 无锁的线程安全整数
 */
public class AtomicIntegerDemo {
    static AtomicInteger i = new AtomicInteger();
    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread[] ts = new Thread[10];
        for (int j = 0; j < 10; j++) {
            ts[j] = new Thread(new AddThread());
        }

        for (int j = 0; j < 10; j++) {
            ts[j].start();
        }
        for (int j = 0; j < 10; j++) {
            ts[j].join();
        }
        System.out.println(i);
    }
}
