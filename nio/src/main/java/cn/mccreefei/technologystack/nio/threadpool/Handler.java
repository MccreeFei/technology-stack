package cn.mccreefei.technologystack.nio.threadpool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MccreeFei
 * @create 2019-02-14 上午9:31
 * 单线程reactor处理read、send。读到内容的逻辑处理统一丢到线程池当中完成
 * 可能存在问题：线程池已满，第一次读事件的业务逻辑处理一直在阻塞，此时主线程同一channel的client又再次发送消息
 * 因为前一个一直在阻塞，state为PROCESSING，所以第二次读不会操作直接返回，channel中一直有可读数据，Reactor中select（）也就不会阻塞，
 * 所以cpu就会爆炸
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

    public Handler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        sk = socketChannel.register(selector, SelectionKey.OP_READ);
        sk.attach(this);
    }
    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            }else if (state == SENDING) {
                send();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean inputIsComplete(){
        return inputByteBuffer.hasRemaining();
    }
    private boolean outputIsComplete() {
        return isDone;
    }

    private synchronized void read() throws IOException {
        socketChannel.read(inputByteBuffer);
        if (inputIsComplete()){
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
        }else {
            state = READING;
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void process() {
        StringBuilder sb = new StringBuilder();
        inputByteBuffer.flip();
        while (inputByteBuffer.hasRemaining()){
            sb.append((char)inputByteBuffer.get());
        }
        String content = sb.toString();
        if (content.startsWith("over")){
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
