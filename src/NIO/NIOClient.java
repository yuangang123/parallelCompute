package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class NIOClient {
    private Selector selector;
    public void init(String ip,int port) throws IOException{
        SocketChannel channel =SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip,port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working()throws Exception{
        while (true){
            if(!selector.isOpen()){
                break;
            }
            selector.select();
            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey key= ite.next();
                ite.remove();
                if (key.isConnectable()){
                    connect(key);
                }else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public void connect(SelectionKey key) throws Exception{
        SocketChannel socketChannel = (SocketChannel)key.channel();
        if(socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        socketChannel.write(ByteBuffer.wrap(new String("hello server\r\n").getBytes()));
        socketChannel.register(this.selector,SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byte[] data = byteBuffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到的消息："+msg);
        channel.close();
        key.selector().close();
    }

    public static void main(String[] args)  throws Exception {
        NIOClient nioClient = new NIOClient();
        nioClient.init("localhost",8001);
        nioClient.working();
    }
}
