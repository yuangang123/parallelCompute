/**
 * Created by 袁刚 on 2017/7/31.
 */

/**
 * 本例子在于展示
 * 线程的优先级
 * java的优先级在1-10之间，
 * 包括几个静态常量
 * MIN_PRIORITY
 * NORM_PRIORITY
 * MIN_PRIORITY
 */
public class PriorityDemo {

    public static class HightPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            synchronized (PriorityDemo.class){
                while (true){
                    count++;
                    if (count>10000000){
                        System.out.println("HightPriority is complete");
                        break;
                    }
                }
            }
        }
    }
    public static class LowPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            synchronized (PriorityDemo.class){
                while (true){
                    count++;
                    if (count>10000000){
                        System.out.println("LowPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new HightPriority();
        Thread t2 = new LowPriority();
        t1.setPriority(Thread.MAX_PRIORITY);

        t2.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
    }
}
