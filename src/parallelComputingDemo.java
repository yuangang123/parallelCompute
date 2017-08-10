import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 袁刚 on 2017/8/7.
 */


/**
 * 并行流水线
 */
class Msg{
    public double   i;
    public double j;
    public String orgStr =null;
}

class Plus implements Runnable{
    public  static  LinkedBlockingQueue<Msg> blockingDeque = new LinkedBlockingQueue<Msg>();

    @Override
    public void run() {
        while (true){
            try{
                Msg msg = blockingDeque.take();
                msg.j = msg.j+msg.i;
                Multiply.blockingQueue.add(msg);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Multiply implements Runnable{
    public static LinkedBlockingQueue<Msg> blockingQueue= new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true){
            try{
                Msg msg =blockingQueue.take();
                msg.i = msg.i*msg.j;
                Div.blockingDeque.add(msg);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Div implements Runnable{
    public static BlockingDeque<Msg> blockingDeque= new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true){
            try{
                Msg msg = blockingDeque.take();
                msg.i = msg.i/2;
                System.out.println(msg.orgStr+"="+msg.i);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}


class parallelComputingDemo {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                Msg msg = new Msg();
                msg.i =i;
                msg.j = j;
                msg.orgStr = "(("+i+"+"+j+")*"+i+")/2";
                Plus.blockingDeque.add(msg);
            }
        }
    }


}
