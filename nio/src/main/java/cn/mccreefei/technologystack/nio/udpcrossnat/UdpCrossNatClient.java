package cn.mccreefei.technologystack.nio.udpcrossnat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author MccreeFei
 * @create 2020-03-07 下午1:18
 *  UDP协议格式 magic[4]+type[1]+...
 *  心跳(type:1)： magic[4]+type[1]+nameLength[4]+name
 *  连接用户(type:2)(客户端->服务端): magic[4]+type[1]+toConnectNameLength[4]+toConnectName+fromNameLength[4]+fromName
 *  连接用户(type:3)(服务端->客户端): magic[4]+type[1]+nameLength[4]+name+hostLength[4]+host+port[4]
 *  普通消息(type:4)(客户端->客户端): magic[4]+type[1]+fromNameLength[4]+fromName+textLength[4]+text
 */
public class UdpCrossNatClient {
    private static final String SERVER_HOST = "www.mccreefei.cn";
    private static final int SERVER_PORT = 8899;
    private static final int MAGIC = 0xBC2020;
    private static ScheduledExecutorService scheduledThreadPoll = Executors.newSingleThreadScheduledExecutor();
    private static DatagramChannel channel;
    private static Map<String, InetSocketAddress> nameAddressMap = new ConcurrentHashMap<>();
    private static String myName;
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("运行请添加用户名参数！");
            return;
        }
        myName = args[0];
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        scheduledThreadPoll.scheduleAtFixedRate(new HeartbeatTask(), 0, 60, TimeUnit.SECONDS);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                String[] split = line.split("\\s+");
                if (split.length != 2) {
                    System.out.println("格式错误！");
                    System.out.println("正确格式 connect [username] 或者 [username] [msg]");
                    continue;
                }
                if ("connect".equalsIgnoreCase(split[0].trim())) {
                    try {
                        sendConnectMsg(split[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String toName = split[0];
                    InetSocketAddress address = nameAddressMap.get(toName);
                    if (address == null) {
                        System.out.println("用户" + toName + "未连接服务器");
                        continue;
                    }
                    try {
                        sendText(address, split[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            buffer.clear();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                SocketAddress remoteAddress = channel.receive(buffer);
                buffer.flip();
                decode((InetSocketAddress) remoteAddress, buffer);
            }
        }


    }

    public static class HeartbeatTask implements Runnable {
        private InetSocketAddress address = new InetSocketAddress(SERVER_HOST, SERVER_PORT);
        @Override
        public void run() {
            try {
                sendHeartbeatMsg(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void decode(InetSocketAddress remoteAddress, ByteBuffer buffer) throws IOException {
        int magic = buffer.getInt();
        if (magic != MAGIC) {
            System.out.println("Magic not match! remote:" + remoteAddress);
            return;
        }
        int type = buffer.get();
        switch (type) {
            case 1:
                int nameLength = buffer.getInt();
                String name = decodeString(buffer, nameLength);
                nameAddressMap.put(name, remoteAddress);
                System.out.println("接收到心跳 来自name:" + name + "地址:" + remoteAddress);
                break;
            case 3:
                int nameSize = buffer.getInt();
                String connectName = decodeString(buffer, nameSize);
                int hostLength = buffer.getInt();
                String host = decodeString(buffer, hostLength);
                int port = buffer.getInt();
                InetSocketAddress address = new InetSocketAddress(host, port);
                nameAddressMap.put(connectName, address);
                sendHeartbeatMsg(address);
                System.out.println("获取到用户" + connectName + "地址信息, 可以与该用户进行P2P交流! host:" + host + ", port:" + port );
                break;
            case 4:
                String fromName = decodeString(buffer, buffer.getInt());
                String text = decodeString(buffer, buffer.getInt());
                System.out.println("接收到来自用户的消息 user:" + fromName + ",text:" + text);
                break;
            default:
                System.out.println("unknown type!, type:" + type);

        }

    }

    private static String decodeString(ByteBuffer buffer, int length) {
        byte[] b = new byte[length];
        buffer.get(b);
        return new String(b, StandardCharsets.UTF_8);
    }

    private static void sendConnectMsg(String toName) throws IOException {
        ByteBuffer byteBuffer = buildConnectBuffer(myName, toName);
        if (byteBuffer == null) {
            System.out.println("build connect buffer error, fromName:" + myName + ",toName:{}" + toName);
            return;
        }
        sendMsg(new InetSocketAddress(SERVER_HOST, SERVER_PORT), byteBuffer);

    }

    private static void sendHeartbeatMsg(InetSocketAddress address) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(MAGIC);
        buffer.put((byte) 1);
        byte[] bytes = myName.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        sendMsg(address, buffer);
    }

    //magic[4]+type[1]+toConnectNameLength[4]+toConnectName+fromNameLength[4]+fromName
    private static ByteBuffer buildConnectBuffer(String fromName, String toName) {
        if (fromName == null || fromName.isEmpty() || toName == null || toName.isEmpty()) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(MAGIC);
        buffer.put((byte) 2);
        byte[] toNameBytes = toName.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(toNameBytes.length);
        buffer.put(toNameBytes);
        byte[] fromNameBytes = fromName.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(fromNameBytes.length);
        buffer.put(fromNameBytes);
        return buffer;
    }

    private static void sendText(InetSocketAddress address, String text) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(MAGIC);
        buffer.put((byte) 4);
        byte[] nameBytes = myName.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(nameBytes.length);
        buffer.put(nameBytes);
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(textBytes.length);
        buffer.put(textBytes);
        sendMsg(address, buffer);
    }

    private static void sendMsg(InetSocketAddress address, ByteBuffer buffer) throws IOException {
        buffer.flip();
        if (address == null) {
            return;
        }
        channel.send(buffer, address);
    }
}
