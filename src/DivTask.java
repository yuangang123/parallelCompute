import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 袁刚 on 2017/8/3.
 */
public class DivTask implements Runnable {
    int a,b;
    public DivTask(int a,int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double re =a/b;
        System.out.println(re);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool  = new ThreadPoolExecutor(0,Integer.MAX_VALUE,0L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i = 0; i <5 ; i++) {
//            pool.submit(new DivTask(100,i));错误被吞掉了
//            pool.execute(new DivTask(100,i));//错误堆栈会显示出来

        }
    }
}
