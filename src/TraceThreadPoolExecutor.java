import java.util.concurrent.*;

/**
 * Created by 袁刚 on 2017/8/3.
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maxnumPoolSize, long keepAliveTime, TimeUnit unit, SynchronousQueue<Runnable> workQueue){
        super(corePoolSize,maxnumPoolSize,keepAliveTime,unit,workQueue);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(wrap(task,clientTrace(),Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
    }

    private Exception clientTrace(){
        return new Exception("client stack trace");
    }

    private Runnable wrap(final Runnable task,final Exception clientStack,String clientName){
        return new Runnable() {
            @Override
            public void run() {
                try{
                    task.run();
                }catch (Exception e){
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };

    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0,Integer.MAX_VALUE,0L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        /**
         * 错误堆栈中可以看到是哪里提交的任务
         */
        for (int i = 0; i <5 ; i++) {
            pools.execute(new DivTask(100,i));
        }
    }
}
