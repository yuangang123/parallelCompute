import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 袁刚 on 2017/8/4.
 */

/**
 * ThreadLocal 人手一根笔
 * 如果当前线程获取不到对象的时候
 * 因为SimpleDateFormat是线程不安全的对象
 * ThreadLocal只是充当容器的作用，为每一个线程分配不同的对象，需要在应用层来保证
 */
public class SimpleDateFormatDemo2 {
    static ThreadLocal<SimpleDateFormat> t1  = new ThreadLocal<SimpleDateFormat>();
    public static  class ParseDate implements Runnable{
        int i =0;
        public ParseDate(int i){
            this.i= i;
        }

        @Override
        public void run() {
            try{
                if(t1.get()==null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = t1.get().parse("2015-03-29 19:29"+i%60);
                System.out.println(i+": "+t);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
    }
}
