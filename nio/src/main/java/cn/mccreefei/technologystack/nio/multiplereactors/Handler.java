package cn.mccreefei.technologystack.nio.multiplereactors;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MccreeFei
 * @create 2019-02-15 下午6:54
 */
public class Handler implements Runnable {
    private final SelectionKey sk;
    private final SocketChannel socketChannel;
    private final static int READING = 1;
    private final static int SENDING = 2;
    private final static int PROCESSING = 3;
    private int state = 1;
    private boolean isDone = false;
    private ByteBuffer inputByteBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer outputByteBuffer = ByteBuffer.allocate(1024);
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private final ReentrantLock selectorLock;

    public Handler(SocketChannel socketChannel, SubReactor subReactor) throws IOException {
        this.socketChannel = socketChannel;
        selectorLock = subReactor.getSelectorLock();
        socketChannel.configureBlocking(false);
        selectorLock.lock();
        try {
            subReactor.getSelector().wakeup();
            sk = socketChannel.register(subReactor.getSelector(), SelectionKey.OP_READ);
        }finally {
            selectorLock.unlock();
        }
        sk.attach(this);
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean inputIsComplete() {
        return inputByteBuffer.hasRemaining();
    }

    private boolean outputIsComplete() {
        return isDone;
    }

    private synchronized void read() throws IOException {
        socketChannel.read(inputByteBuffer);
        if (inputIsComplete()) {
            state = PROCESSING;
            threadPool.submit(new Processor());
        }
    }

    private void send() throws IOException {
        outputByteBuffer.flip();
        socketChannel.write(outputByteBuffer);
        outputByteBuffer.clear();
        if (outputIsComplete()) {
            sk.cancel();
            socketChannel.close();
        } else {
            state = READING;
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void process() {
        StringBuilder sb = new StringBuilder();
        inputByteBuffer.flip();
        while (inputByteBuffer.hasRemaining()) {
            sb.append((char) inputByteBuffer.get());
        }
        String content = sb.toString();
        if (content.startsWith("over")) {
            isDone = true;
        }
        System.out.println("Client Message : " + content);
        outputByteBuffer.put(content.getBytes());
        inputByteBuffer.clear();
    }

    private synchronized void processAndHandOff() {
        process();
        state = SENDING;
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.selector().wakeup();
        System.out.println("Process over");
    }

    class Processor implements Runnable {

        @Override
        public void run() {
            processAndHandOff();
        }
    }
}
