package cn.mccreefei.technologystack.rpc.client.netty;

import cn.mccreefei.technologystack.rpc.client.core.RpcRequestPool;
import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午3:13
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Resource
    private RpcRequestPool requestPool;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        log.info("response from server : " + rpcResponse);
        requestPool.notifyRequest(rpcResponse.getRequestId(), rpcResponse);
    }
}
