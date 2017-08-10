import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by 袁刚 on 2017/8/5.
 */
public class AtomicInterArrayDemo {
    static AtomicIntegerArray arr = new AtomicIntegerArray(10);
    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                arr.getAndIncrement(i%arr.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread [] threads= new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new AddThread());
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println(arr);
    }
}
