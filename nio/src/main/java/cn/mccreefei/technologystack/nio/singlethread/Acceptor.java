package cn.mccreefei.technologystack.nio.singlethread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author MccreeFei
 * @create 2019-02-13 上午10:42
 */
public class Acceptor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel){
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null){
                System.out.println("new connection " + socketChannel.getRemoteAddress());
                new Handler(selector, socketChannel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
