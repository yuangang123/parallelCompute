/**
 * Created by 袁刚 on 2017/7/31.
 */

/**
 * 本程序旨在展示线程组的作用
 */
public class ThreadGroupName implements Runnable{
    @Override
    public void run() {
        String gropAndName = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        while (true) {
            System.out.println("I am " + gropAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("printGroup");
        Thread t1 = new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2 = new Thread(tg,new ThreadGroupName(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();

    }
}
