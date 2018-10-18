package cn.mccreefei.technologystack.rpc.server.netty;

import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author MccreeFei
 * @create 2018-10-15 上午11:10
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Resource
    private ApplicationContext context;
    @Resource
    private Serialization serialization;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        log.info("request from client : " + rpcRequest);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            String className = rpcRequest.getClassName();
            Object target = context.getBean(Class.forName(className));
            Method targetMethod = target.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Object result = targetMethod.invoke(target, rpcRequest.getParameters());
            rpcResponse.setResult(result);
        }catch (Exception e){
            log.error("RpcRequestHandler Error!", e);
            rpcResponse.setException(e);
        }
        ctx.writeAndFlush(rpcResponse);

    }
}
