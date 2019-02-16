package cn.mccreefei.technologystack.nio.multiplereactors;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MccreeFei
 * @create 2019-02-15 下午6:28
 */
public class SubReactor implements Runnable {
    private final Selector selector;
    private final ReentrantLock selectorLock = new ReentrantLock();

    public SubReactor(Selector selector){
        this.selector = selector;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                /**
                 * 必须加这个锁，否则selector.register()会一直阻塞
                 * 如果同一个线程下的register、select操作不会阻塞
                 * 具体参考stackoverflow<href> https://stackoverflow.com/questions/1057224/java-thread-blocks-while-registering-channel-with-selector-while-select-is-cal/2179612#2179612
                 * </href>
                 */
                selectorLock.lock();
                selectorLock.unlock();
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

    public Selector getSelector() {
        return selector;
    }

    private void dispatch(SelectionKey selectionKey){
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }

    public ReentrantLock getSelectorLock() {
        return selectorLock;
    }
}
