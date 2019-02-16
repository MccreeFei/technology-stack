package cn.mccreefei.technologystack.nio.multiplereactors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2019-02-15 下午6:18
 */
public class MainReactor implements Runnable {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private final SubReactor[] subReactors;

    public MainReactor(int port, int subReactorNumber) throws IOException {
        //init subReactors
        subReactors = new SubReactor[subReactorNumber];
        for (int i = 0; i < subReactorNumber; i++) {
            Selector selector = Selector.open();
            subReactors[i] = new SubReactor(selector);
            new Thread(subReactors[i], "SubReactor-thread-" + i).start();
        }

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor(serverSocketChannel, subReactors));
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    dispatch(sk);
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey sk) {
        Runnable runnable = (Runnable) sk.attachment();
        runnable.run();
    }

    public static void main(String[] args) throws IOException {
        new Thread(new MainReactor(9001, 3), "MainReactor-thread").start();
        System.out.println("Server Start");
    }
}
