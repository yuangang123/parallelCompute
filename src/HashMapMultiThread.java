/**
 * Created by 袁刚 on 2017/7/31.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 验证hashMap的线程安全性
 */
public class HashMapMultiThread {
    static Map<String,String> map= new HashMap<>();
    public static class AddThread implements Runnable{

        int start =0;
        public AddThread(int start){
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i <100000 ; i+=2) {
                map.put(Integer.toString(i),Integer.toString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));
        t1.start();
        t2.start();
        t1.join();

        System.out.println(map.size());
    }
}