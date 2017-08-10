/**
 * Created by 袁刚 on 2017/8/7.
 */

/**
 * 解决伪共享CPU cache
 */
public class FalseShareing implements Runnable{
    public final static int NUM_THREADS = 2;
    public final static long ITERATIONS = 500l*1000l*1000l;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i]= new VolatileLong();
        }
    }


    public FalseShareing(final int arrayIndex){
        this.arrayIndex = arrayIndex;
    }

    public static void main(String[] args) throws Exception{
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration="+(System.currentTimeMillis()-start));
    }


    private static void runTest() throws InterruptedException{
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseShareing(i));

        }
        for (Thread t:threads){
            t.start();
        }
        for (Thread t: threads){
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS+1;
        while (0!=--i){
            longs[arrayIndex].value=i;
        }
    }

    public final static class VolatileLong{

        public volatile long value=0L;
//        public long p1,p2,p3,p4,p5,p6,p7;
    }
}
