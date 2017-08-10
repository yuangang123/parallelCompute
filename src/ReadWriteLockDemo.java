/**
 * Created by 袁刚 on 2017/8/1.
 */

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 读-读：不互斥，读读之间不阻塞
 * 读-写：互斥，读会阻塞写，写也会阻塞读
 * 写-写：互斥，写写之间会相互阻塞
 */

/**
 * 读写锁的效率相比可重入锁的效率好多了
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock= readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException{
        try{
            lock.lock();
            Thread.sleep(1000);
            return value;
        }finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock,int index) throws  InterruptedException{
        try{
            lock.lock();
            Thread.sleep(1000);
            value = index;
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try{
//                    demo.handleRead(readLock);
                    demo.handleRead(lock);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunable =new Runnable() {
            @Override
            public void run() {
                try{
//                    demo.handleWrite(writeLock,new Random().nextInt());
                    demo.handleWrite(lock,new Random().nextInt());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }

        for (int i = 0; i <2 ; i++) {
            new Thread(writeRunable).start();

        }
    }
}
