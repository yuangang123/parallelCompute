import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class FutureDemo2 {
    public static class RealData implements Callable<String>{
        private String para;
        public RealData(String para)
        {
            this.para = para;
        }

        @Override
        public String call() throws Exception {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                sb.append(para);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws Exception{
        FutureTask<String> futureTask = new FutureTask<String>(new RealData("a"));
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.execute(futureTask);
        System.out.println("请求操作完毕");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("数据="+futureTask.get());
    }
}
