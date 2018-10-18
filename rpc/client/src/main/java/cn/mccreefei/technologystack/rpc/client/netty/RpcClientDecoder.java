package cn.mccreefei.technologystack.rpc.client.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午3:08
 * Decode server bytes to RpcResponse
 */
public class RpcClientDecoder extends ByteToMessageDecoder {
    private Serialization serialization;
    public RpcClientDecoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() <= 4){
            return;
        }
        in.markReaderIndex();
        int length = in.readInt();
        if (in.readableBytes() < length){
            in.resetReaderIndex();
        }else {
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            RpcResponse rpcResponse = serialization.deSerialize(bytes, RpcResponse.class);
            out.add(rpcResponse);
        }
    }
}
