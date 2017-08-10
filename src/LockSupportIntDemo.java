import java.util.concurrent.locks.LockSupport;

/**
 * Created by 袁刚 on 2017/8/3.
 */
public class LockSupportIntDemo {
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("In "+getName());
                LockSupport.park();
                if (Thread.interrupted()){
                    System.out.println(getName()+"被中断了");
                }
            }
            System.out.println("执行结束");
        }
    }

    public static Object u  =new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");


    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }

}
