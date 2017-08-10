/**
 * Created by 袁刚 on 2017/7/31.
 */

/**
 * 演示suspend()所产生的问题
 * 它占用的锁不会被释放，因此可能导致整个系统不会工作
 */
public class BadSuspend {
    public static Object u  =new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2= new ChangeObjectThread("t2");


    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.resume();
        //由于时间关系，t2.resume()并没我执行
//        Thread.sleep(1000);若添加这样一句，让先后顺序得到保障，就能够缓解t2被挂起所带来的程序问题
        t2.resume();
        //由于t2线程处于了挂起状态，所以，其所占有的资源不会得到释放
        //所以后面join无法得到执行，程序被挂起。
        t1.join();
        t2.join();
    }
}
