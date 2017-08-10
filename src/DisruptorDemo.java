import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * 用Disruptor实现生产者和消费者的案例
 *
 * 在disruptor中别出心裁的使用了环形RinBuffer来代替普通线性队列
 */
public class DisruptorDemo {
    /**
     * Created by 袁刚 on 2017/8/6.
     */
    public static class PCData{
        private long value;

        public void setValue(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    /**
     * 消费者实现为workHandle接口，他来自Disruptor框架
     */
    public static class  Consumer implements WorkHandler<PCData>{
        @Override
        public void onEvent(PCData pcData) throws Exception {
            System.out.println(Thread.currentThread().getId()+":event: --"+pcData.getValue()*pcData.getValue()+"--");
        }
    }

    /**
     * 产生一个PCData工厂类，他会在Disruptor系统初始化的时候，构造所有缓冲区中的对象实例（之前说过Disruptor会预先分配空间）
     */
    public static class PCDataFactory implements EventFactory<PCData>{
        @Override
        public PCData newInstance() {
            return new PCData();
        }
    }

    public static class Producer{
        private final RingBuffer<PCData> ringBuffer;

        public Producer(RingBuffer<PCData> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void pushData(ByteBuffer bb){
            long sequence  = ringBuffer.next();
            try{
                PCData event = ringBuffer.get(sequence);
                event.setValue(bb.getLong(0));
            }finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Executor executor= Executors.newCachedThreadPool();
        PCDataFactory factory = new PCDataFactory();

        int bufferSize = 1024;
        /**
         * Disruptor提供了几种策略
         * BLockingWaitStrategy
         * SleepingWaitStrategy
         * YieldingWaitStrategy
         * BusySpinWaitStrategy
         */
        Disruptor<PCData> disruptor= new Disruptor<PCData>(factory,bufferSize,executor, ProducerType.MULTI,new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new Consumer(),new Consumer(),new Consumer(),new Consumer());
        disruptor.start();

        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer bb= ByteBuffer.allocate(8);
        for (long i = 0;true ; i++) {
            bb.putLong(0,i);
            producer.pushData(bb);
            Thread.sleep(100);
            System.out.println("add data "+i);
        }
    }
}
