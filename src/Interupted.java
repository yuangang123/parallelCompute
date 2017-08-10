/**
 * Created by 袁刚 on 2017/7/31.
 */
public class Interupted {
    public static void main(String[] args)throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
//                super.run();
                while (true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted");
                        break;
                    }
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        System.out.println("Interrupted when sleep");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
