package cn.mccreefei.technologystack.rpc.server.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author MccreeFei
 * @create 2018-10-15 上午9:19
 * server netty 初始化
 */
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Resource
    private RpcRequestHandler rpcRequestHandler;
    @Resource
    private Serialization serialization;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new RpcServerDecoder(serialization));
        pipeline.addLast(new RpcServerEncoder(serialization));
        pipeline.addLast(rpcRequestHandler);
    }
}
