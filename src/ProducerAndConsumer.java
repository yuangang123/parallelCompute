import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 袁刚 on 2017/8/6.
 */

/**
 * 生产者和消费者模式
 * 使用BlockingQueue做内存缓冲区
 */

/**
 * 生产者线程实现如下
 * 构建了PCData
 * 并放入BlockingQueue队列中
 */
class Producer implements Runnable{
    private volatile boolean isRunning = true;
    private BlockingDeque<PCData> queue;
    private static AtomicInteger count= new AtomicInteger();
    private static final int SLEEPTME = 1000;

    public Producer(BlockingDeque<PCData> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        PCData data = null;
        Random r = new Random();
        System.out.println("start producer id:"+Thread.currentThread().getId());
        try{
            while (isRunning){
                Thread.sleep(r.nextInt(SLEEPTME));
                data= new PCData(count.incrementAndGet());
                System.out.println(data+"is put into queue");
                if (!queue.offer(data,2, TimeUnit.SECONDS)){
                    System.err.println(data+" is put into queue");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop(){
        isRunning=false;
    }
}

/**
 * 对应的消费者的实现如下，它从BlockingQueue队列中取出PCData对象
 * 并进行对应的计算
 */
class Consumer implements Runnable{
    private BlockingDeque<PCData> queue;
    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingDeque<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id="+Thread.currentThread().getId());
        Random r= new Random();

        try{
            while (true){
                PCData data = queue.take();
                if (null!=data){
                    int re = data.getDate()*data.getDate();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getDate(),data.getDate(),re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * PCData作为生产者和消费者之间共享的数据模型
 */
class PCData{
    private final int intDate;
    public PCData(int d){
        this.intDate =d;
    }
    public PCData(String d){
        this.intDate= Integer.valueOf(d);
    }

    public int getDate() {
        return intDate;
    }

    @Override
    public String toString() {
        return "data:"+intDate;
    }

    public static void main(String[] args) throws Exception{
        BlockingDeque<PCData> queue = new LinkedBlockingDeque<PCData>(10);
        Producer producer1= new Producer(queue);
        Producer producer2= new Producer(queue);
        Producer producer3= new Producer(queue);
        Consumer consumer1= new Consumer(queue);
        Consumer consumer2= new Consumer(queue);
        Consumer consumer3= new Consumer(queue);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(producer1);
        es.execute(producer2);
        es.execute(producer3);
        es.execute(consumer1);
        es.execute(consumer2);
        es.execute(consumer3);
        Thread.sleep(10*1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();
        Thread.sleep(3000);
        es.shutdown();
    }
}