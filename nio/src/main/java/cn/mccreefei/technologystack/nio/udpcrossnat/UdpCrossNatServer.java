package cn.mccreefei.technologystack.nio.udpcrossnat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-03-06 下午5:50
 * UDP协议格式 magic[4]+type[1]+...
 * 心跳(type:1)： magic[4]+type[1]+nameLength[4]+name
 * 连接用户(type:2)(客户端->服务端): magic[4]+type[1]+toConnectNameLength[4]+toConnectName+fromNameLength[4]+fromName
 * 连接用户(type:3)(服务端->客户端): magic[4]+type[1]+nameLength[4]+name+hostLength[4]+host+port[4]
 * 普通消息(type:4)(客户端->客户端): magic[4]+type[1]+fromNameLength[4]+fromName+textLength[4]+text
 */
public class UdpCrossNatServer {
    private static final int SERVER_PORT = 8899;
    private static final int MAGIC = 0xBC2020;
    private static Map<String, InetSocketAddress> nameAddressMap = new HashMap<>();
    private static DatagramChannel channel;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(SERVER_PORT));
        channel.register(selector, SelectionKey.OP_READ);
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
            case 2:
                int toConnectNameLength = buffer.getInt();
                String toConnectName = decodeString(buffer, toConnectNameLength);
                int fromNameLength = buffer.getInt();
                String fromName = decodeString(buffer, fromNameLength);
                nameAddressMap.put(fromName, remoteAddress);
                System.out.println("接收到连接消息 fromName:" + fromName +
                        ",toName:" + toConnectName);
                sendConnectMsg(fromName, toConnectName);
                sendConnectMsg(toConnectName, fromName);
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

    private static void sendConnectMsg(String fromName, String toName) throws IOException {
        InetSocketAddress fromAddress = nameAddressMap.get(fromName);
        ByteBuffer byteBuffer = buildConnectBuffer(fromName, fromAddress);
        if (byteBuffer == null) {
            System.out.println("build connect buffer error, fromName:" + fromName);
            return;
        }
        sendMsgToUser(toName, byteBuffer);

    }

    //magic[4]+type[1]+nameLength[4]+name+hostLength[4]+host+port[4]
    private static ByteBuffer buildConnectBuffer(String name, InetSocketAddress address) {
        if (name == null || name.isEmpty() || address == null) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(MAGIC);
        buffer.put((byte) 3);
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(nameBytes.length);
        buffer.put(nameBytes);
        byte[] addressBytes = address.getHostString().getBytes(StandardCharsets.UTF_8);
        buffer.putInt(addressBytes.length);
        buffer.put(addressBytes);
        buffer.putInt(address.getPort());
        return buffer;
    }

    private static void sendMsgToUser(String name, ByteBuffer buffer) throws IOException {
        buffer.flip();
        InetSocketAddress remoteAddress = nameAddressMap.get(name);
        if (remoteAddress == null) {
            System.out.println("连接用户未上过线:" + name);
            return;
        }
        channel.send(buffer, remoteAddress);
    }
}
