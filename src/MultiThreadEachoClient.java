import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class MultiThreadEachoClient {
    public static void main(String[] args) {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader bufferedReader = null;

        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost",8000));
            writer = new PrintWriter(client.getOutputStream(),true);
            writer.println("Hello" +
                    "ewewee" +
                    "wewewe" +
                    "e" +
                    "we" +
                    "we" +
                    "w" +
                    "e");
            writer.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server:"+bufferedReader.readLine());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{

                if (writer!=null) writer.close();
                if (bufferedReader!=null) bufferedReader.close();
                if (client!=null) client.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
