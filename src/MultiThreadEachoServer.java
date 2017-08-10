import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class MultiThreadEachoServer {
    private static ExecutorService tp  = Executors.newCachedThreadPool();
    static class HandleMsg implements Runnable{
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is =null;
            PrintWriter os = null;
            try{
                is= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os =new PrintWriter(clientSocket.getOutputStream(),true);
                String inputLine = null;
                long b = System.currentTimeMillis();
                    while ((inputLine=is.readLine())!=null){
                        os.println(inputLine);
                }
                long e= System.currentTimeMillis();
                System.out.println("spend:"+(e-b)+"ms");
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{

                    if(is!=null){
                        is.close();
                    }
                    if (os!=null){
                        os.close();
                    }
                    clientSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientsocket =null;
        try{
            serverSocket = new ServerSocket(8000);
        }catch (IOException e){
            e.printStackTrace();
        }
        while (true){
            try{
                clientsocket = serverSocket.accept();
                System.out.println(clientsocket.getRemoteSocketAddress()+" connect!");
                tp.execute(new HandleMsg(clientsocket));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
