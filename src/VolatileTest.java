/**
 * Created by 袁刚 on 2017/7/31.
 */
public class VolatileTest {
    static volatile int i = 0;
    public static class PlusTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j <10000 ; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args)throws  InterruptedException {
        Thread[] threads =  new Thread[10];
        for (int j = 0; j <10 ; j++) {
            threads[j] = new Thread(new PlusTask());
            threads[j].start();
        }
        for (int j = 0; j <10 ; j++) {
            threads[j].join();
        }

        //如果使用volatile修饰后，保持可见性和原子性的话，结果应该是100000，但是结果通常是小于100000的
        //无法保证符合操作的原子性
        System.out.println(i);
    }
}
