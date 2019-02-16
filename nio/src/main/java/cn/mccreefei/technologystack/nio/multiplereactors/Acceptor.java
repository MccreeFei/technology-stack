package cn.mccreefei.technologystack.nio.multiplereactors;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author MccreeFei
 * @create 2019-02-15 下午6:15
 */
public class Acceptor implements Runnable {
    private final ServerSocketChannel serverSocketChannel;
    private final SubReactor[] subReactors;
    private final int subReactorNumber;
    private int counter = 0;
    public Acceptor(ServerSocketChannel serverSocketChannel, SubReactor[] subReactors){
        this.serverSocketChannel = serverSocketChannel;
        this.subReactors = subReactors;
        this.subReactorNumber = subReactors.length;
    }
    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null){
                System.out.println("new connection " + socketChannel.getRemoteAddress());
                SubReactor subReactor = subReactors[counter++ % subReactorNumber];
                new Handler(socketChannel, subReactor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
