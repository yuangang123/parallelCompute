import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class HeavySocketClient {
    private static ExecutorService tp = Executors.newCachedThreadPool();
    private static final int sleep_time =1000*1000*1000;
    public static class EchoClient implements Runnable{
        @Override
        public void run() {
            Socket client = null;
            PrintWriter printWriter = null;
            BufferedReader bufferedReader = null;
            try{
                client =new Socket();
                client.connect(new InetSocketAddress("localhost",8001));
                printWriter = new PrintWriter(client.getOutputStream(),true);
                printWriter.print("H");
                LockSupport.parkNanos(sleep_time);

                printWriter.print("e");
                LockSupport.parkNanos(sleep_time);

                printWriter.print("l");
                LockSupport.parkNanos(sleep_time);

                printWriter.print("l");
                LockSupport.parkNanos(sleep_time);

                printWriter.print("o");
                LockSupport.parkNanos(sleep_time);

                printWriter.println();
                printWriter.flush();

                bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:"+bufferedReader.readLine());

            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                    if (printWriter!=null) printWriter.close();
                    if (bufferedReader!=null) bufferedReader.close();
                    if (client!=null) client.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        for (int i = 0; i < 10; i++) {
            tp.execute(echoClient);
        }
    }
}
