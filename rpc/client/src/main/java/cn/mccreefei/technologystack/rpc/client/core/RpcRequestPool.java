package cn.mccreefei.technologystack.rpc.client.core;

import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午2:46
 * 需求池
 */
@Component
public class RpcRequestPool {
    private final Map<String, Promise<RpcResponse>> requestPool = new ConcurrentHashMap<>();

    public void addRequest(String requestId, EventExecutor executor){
        requestPool.put(requestId, new DefaultPromise<RpcResponse>(executor));
    }

    public RpcResponse getResponse(String requestId) throws Exception {
        //获取远程调用结果 10s超时
        RpcResponse rpcResponse = requestPool.get(requestId).get(10, TimeUnit.SECONDS);
        requestPool.remove(requestId);
        return rpcResponse;
    }

    public void notifyRequest(String requestId, RpcResponse rpcResponse){
        Promise<RpcResponse> promise = requestPool.get(requestId);
        if (promise != null){
            promise.setSuccess(rpcResponse);
        }
    }
}
