package cn.mccreefei.technologystack.rpc.server.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author MccreeFei
 * @create 2018-10-15 上午11:27
 * Encode RpcResponse to bytes
 */
@Slf4j
public class RpcServerEncoder extends MessageToByteEncoder<RpcResponse> {
    private Serialization serialization;
    public RpcServerEncoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse rpcResponse, ByteBuf out) throws Exception {
        log.info("send response to client : " + rpcResponse);
        byte[] bytes = serialization.serialize(rpcResponse);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
