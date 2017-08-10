import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 袁刚 on 2017/8/1.
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(false);

    @Override
    public void run() {
        while (true){
            try{
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+": 获得锁");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1=new FairLock();
        Thread t1 =new Thread(r1,"Thread-t1");
        Thread t2 = new Thread(r1,"Thread-t2");
        t1.start();
        t2.start();

    }

}
