import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by 袁刚 on 2017/8/4.
 */
public class ThreadLocalDemo_Gc {
    static volatile  ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable {
//            super.finalize();
            System.out.println(this.toString()+"is gc");
        }
    };
    //计数倒数
    static volatile CountDownLatch  cd = new CountDownLatch(10000);
    public static class ParseDate implements Runnable{

        int i =0;
        public ParseDate(int i){
            this.i = i;
        }
        @Override
        public void run() {
            try{
                if (t1.get()==null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        @Override
                        protected void finalize() throws Throwable {
//                            super.finalize();
                            System.out.println(this.toString()+"is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId()+"create SimpleDateFormat");
                }
                Date t =t1.get().parse("2015-03-29 19:29:"+i%60);
            }catch (ParseException e){
                e.printStackTrace();
            }finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args)throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i <10000 ; i++) {
            es.execute(new ParseDate(i));
        }
        //等待10000个任务提交完毕
        cd.await();
        System.out.println("mission complete");
        t1 = null;
        System.gc();
        System.out.println("first GC complete");

        //在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
        /**
         * 如果对于ThreadLocal变量，我们也手动将其设置成为null，比如t1=null，那么这个ThreadLocal对应的所有的线程的局部变量都有可能会被回收
         */
        t1 = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete!");
    }
}
