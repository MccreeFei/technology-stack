package cn.mccreefei.technologystack.nio.singlethread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2019-02-13 上午10:29
 */
public class Reactor implements Runnable{
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor(selector, serverSocketChannel));
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
                /**
                 * 为什么selectionKeys.clear()?
                 * There are 2 tables in the selector:
                 *
                 * registration table: when we call channel.register, there will be a new item(key) into it.
                 * Only if we call key.cancel(), it will be removed from this table.
                 *
                 * ready for selection table: when we call selector.select(), the selector will look up the registration table,
                 * find the keys which are available, copy the references of them to this selection table.
                 * The items of this table won't be cleared by selector(that means, even if we call selector.select() again,
                 * it won't clear the existing items)
                 *
                 * That's why we have to invoke iter.remove() when we got the key from selection table.
                 * If not, we will get the key again and again by selector.selectedKeys() even if it's not ready to use.
                 */
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey sk){
        Runnable runnable = (Runnable) sk.attachment();
        if (runnable != null){
            runnable.run();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(9001)).start();
        System.out.println("Server Start");

    }
}
