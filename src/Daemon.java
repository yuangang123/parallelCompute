/**
 * Created by 袁刚 on 2017/7/31.
 */
public class Daemon {
    public static class DaemonT extends Thread{
        @Override
        public void run() {
//            super.run();
            while (true){
                System.out.println(" i am alive");

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t = new DaemonT();
//        t.setDaemon(true);
        t.start();

        Thread.sleep(2000);
    }
}
