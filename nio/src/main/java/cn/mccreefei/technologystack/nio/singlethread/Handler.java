package cn.mccreefei.technologystack.nio.singlethread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author MccreeFei
 * @create 2019-02-13 上午10:52
 */
public class Handler implements Runnable {
    private final SocketChannel socketChannel;
    private final SelectionKey sk;
    private static final int READING = 1;
    private static final int SENDING = 2;
    private int state = 1;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer outputBuffer = ByteBuffer.allocate(1024);
    private boolean isDone = false;

    public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        sk = socketChannel.register(selector, SelectionKey.OP_READ);
        sk.attach(this);
//        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean inputIsComplete(){
        return inputBuffer.hasRemaining();
    }

    private boolean outputIsComplete(){
        return isDone;
    }

    private void read() throws IOException {
        socketChannel.read(inputBuffer);
        if (inputIsComplete()){
            process();
            inputBuffer.clear();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
//            sk.selector().wakeup();
        }
    }

    private void send() throws IOException{
        outputBuffer.flip();
        socketChannel.write(outputBuffer);
        if (outputIsComplete()){
            sk.cancel();
            socketChannel.close();
        }else {
            outputBuffer.clear();
            state = READING;
            sk.interestOps(SelectionKey.OP_READ);
//            sk.selector().wakeup();
        }
    }

    private void process(){
        inputBuffer.flip();
        StringBuilder sb = new StringBuilder();
        while (inputBuffer.hasRemaining()){
            sb.append((char)inputBuffer.get());
        }
        String content = sb.toString();
        if (content.startsWith("over")){
            isDone = true;
        }
        System.out.println("Client Message : " + content);
        outputBuffer.put(sb.toString().getBytes());
        System.out.println("Process over");
    }
}
