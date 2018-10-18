package cn.mccreefei.technologystack.rpc.client.core;

import cn.mccreefei.technologystack.rpc.client.netty.RpcClientDecoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcClientEncoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcResponseHandler;
import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author MccreeFei
 * @create 2018-10-15 下午3:12
 */
@Component
@Slf4j
public class RpcClient {
    private Channel channel;
    @Value("${netty.host}")
    private String serverHost;
    @Value("${netty.port}")
    private int serverPort;
    private EventLoopGroup loopGroup = new NioEventLoopGroup();
    @Resource
    private RpcRequestPool rpcRequestPool;
    @Resource
    private Serialization serialization;
    @Resource
    private RpcResponseHandler rpcResponseHandler;

    private  void connect() {
        Bootstrap bootstrap = new Bootstrap();
        if (loopGroup == null){
            loopGroup = new NioEventLoopGroup();
        }
        bootstrap.channel(NioSocketChannel.class)
                .group(loopGroup)
                .option(ChannelOption.SO_KEEPALIVE , true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcClientEncoder(serialization));
                        pipeline.addLast(new RpcClientDecoder(serialization));
                        pipeline.addLast(rpcResponseHandler);
                    }
                })
                .remoteAddress(serverHost, serverPort);

        this.channel = bootstrap.connect().channel();
    }

    private  boolean checkActive() {
        if (channel == null || !channel.isActive()){
            synchronized (this) {
                connect();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return channel.isActive();
    }

    public void send(RpcRequest request) {
        if (!checkActive()){
            log.error("client connect to server failed!");
            throw new IllegalStateException("Can not connect to rpc server!");
        }
        log.info("client connect to server success!");
        //请求入池
        rpcRequestPool.addRequest(request.getRequestId(), channel.eventLoop());
        channel.writeAndFlush(request);
    }

    public synchronized void close(){
        if (channel != null){
            channel.close();
            channel = null;
        }
        if (loopGroup != null){
            loopGroup.shutdownGracefully();
            loopGroup = null;
        }
    }
}
