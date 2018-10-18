package cn.mccreefei.technologystack.rpc.server.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-10-15 上午9:25
 * Decode client bytes to RpcRequest
 */
public class RpcServerDecoder extends ByteToMessageDecoder {
    private Serialization serialization;

    public RpcServerDecoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() <= 4) {
            return;
        }
        int length = in.readInt();
        in.markReaderIndex();
        if (in.readableBytes() < length){
            in.resetReaderIndex();
        }else {
            byte[] dst = new byte[length];
            in.readBytes(dst);
            RpcRequest rpcRequest = serialization.deSerialize(dst, RpcRequest.class);
            out.add(rpcRequest);
        }

    }
}
