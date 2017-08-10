import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Created by 袁刚 on 2017/8/3.
 */

/**
 * fork/join框架
 * 分而治之
 * fork()开线程
 * join()等待线程结束
 * 在jdk中，给出了一个FORKJOINPOOL线程池，对于fork()方法并不着急开启线程，而是提交给forkjoinpool线程池进行处理
 */
public class CountTask extends RecursiveTask<Long> {
    private  static final int THRESHOLD =1000;
    private long start;
    private long end;

    public CountTask(long start,long end){
        super();
        this.start = start;
        this.end = end;
    }


    public Long compute(){
        long sum=0;
        boolean canCompute=(end-start)<THRESHOLD;
        if (canCompute){
            for (long i = start; i <=end ; i++) {
                sum+=i;
            }
        }else {
            //分成100个小任务
            long step= (end-start)/100;
            ArrayList<CountTask> subTasks =new ArrayList<CountTask>();
            long pos =start;
            for (int i = 0; i <100 ; i++) {
                long lastOne = pos+step;
                if (lastOne>end){
                    lastOne= end;
                }
                CountTask subTask= new CountTask(pos,lastOne);
                pos+=step+1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask t:subTasks){
                long tmp=t.join();
                sum+=tmp;
            }
        }
        return sum;
//        long sum=0;
//        boolean canCompute=(end - start)<THRESHOLD;
//        if(canCompute){
//            for (long i = start; i < end; i++) {
//                sum+=i;
//            }
//
//        }else{
//            long step=(end - start)/100;
//            ArrayList<CountTask> sunTasks = new ArrayList<CountTask>();
//            long pos=start;
//            for (int i = 0; i <100; i++) {
//                long lastOne=pos+step;
//                if(lastOne>end){
//                    lastOne=end;
//                }
//                CountTask sunTask=new CountTask(pos,lastOne);
//                pos+=step+1;
//                sunTasks.add(sunTask);
//                sunTask.fork();
//            }
//            for (CountTask t : sunTasks) {
//                sum+=t.join();
//            }
//
//
//        }
//
//        return sum;
    }

    public static void main(String[] args){
       ForkJoinPool forkJoinPool= new ForkJoinPool();
        CountTask task  = new CountTask(0,200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long res = result.get();
            System.out.println("sum="+res);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        long result2 = 0;
        for (int i = 0; i <=200000L ; i++) {
            result2+=i;
        }
        System.out.println("result2:"+result2);
    }
}
//
//public class CountTask extends RecursiveTask<Long> {
//    private static final int THRESHOLD=1000;
//    private long start;
//    private long end;
//
//
//    public CountTask(long start, long end) {
//        super();
//        this.start = start;
//        this.end = end;
//    }
//
//
//    @Override
//    protected Long compute() {
//        long sum=0;
//        boolean canCompute=(end - start)<THRESHOLD;
//        if(canCompute){
//            for (long i = start; i < end; i++) {
//                sum+=i;
//            }
//
//        }else{
//            long step=(end - start)/100;
//            ArrayList<CountTask> sunTasks = new ArrayList<CountTask>();
//            long pos=start;
//            for (int i = 0; i <100; i++) {
//                long lastOne=pos+step;
//                if(lastOne>end){
//                    lastOne=end;
//                }
//                CountTask sunTask=new CountTask(pos,lastOne);
//                pos+=step+1;
//                sunTasks.add(sunTask);
//                sunTask.fork();
//            }
//            for (CountTask t : sunTasks) {
//                sum+=t.join();
//            }
//
//
//        }
//
//        return sum;
//    }
//
//
//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//
//        CountTask countTask = new CountTask(0,200000L);
//
//        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
//
//        Long res = result.get();
//        System.out.println(res);
//
//
//
//    }
//
//}
