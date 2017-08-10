package AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class AIOServer {
    public final static int PORT = 8000;
    private AsynchronousServerSocketChannel serverSocketChannel ;
    public AIOServer() throws IOException {
        serverSocketChannel= AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start() throws InterruptedException,ExecutionException,TimeoutException{
        System.out.println("Server listen on"+PORT);
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try{
                    byteBuffer.clear();
                    result.read(byteBuffer).get(100, TimeUnit.SECONDS);
                    byteBuffer.flip();
                    writeResult = result.write(byteBuffer);
                }catch (InterruptedException|ExecutionException e){
                    e.printStackTrace();
                }catch (TimeoutException e){
                    e.printStackTrace();
                }
                finally {
                    try{
                        serverSocketChannel.accept(null,this);
                        writeResult.get();
                        result.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed:"+exc);
            }
        });
    }
}
