package cn.mccreefei.technologystack.rpc.client.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午3:05
 * Encode Client RpcRequest to bytes
 */
@Slf4j
public class RpcClientEncoder extends MessageToByteEncoder<RpcRequest>{
    private Serialization serialization;
    public RpcClientEncoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, ByteBuf out) throws Exception {
        log.info("send request to server : " + rpcRequest);
        byte[] bytes = serialization.serialize(rpcRequest);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
