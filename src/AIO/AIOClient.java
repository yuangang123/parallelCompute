package AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class AIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
         AsynchronousServerSocketChannel client = AsynchronousServerSocketChannel.open();
        client.connect(new InetSocketAddress("localhost",8000),null,new CompletionHandler<Void,Object>(){
            @Override
            public void completed(Void result, Object attachment) {
                client.write(ByteBuffer.wrap("Hello!".getBytes()),null,new CompletionHandler<Integer,Object>(){
                    @Override
                    public void completed(Integer result, Object attachment) {
                        try{
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            client.read(byteBuffer,byteBuffer,new CompletionHandler<Integer,ByteBuffer>(){
                                @Override
                                public void completed(Integer result, ByteBuffer attachment) {
                                    byteBuffer.flip();
                                    System.out.println(new String(byteBuffer.array()));
                                    try {
                                        client.close();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {

                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {

                    }
                });

            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
        Thread.sleep(1000);
    }
}
