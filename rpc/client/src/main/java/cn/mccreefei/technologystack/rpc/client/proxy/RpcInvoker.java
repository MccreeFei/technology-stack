package cn.mccreefei.technologystack.rpc.client.proxy;

import cn.mccreefei.technologystack.rpc.client.core.RpcClient;
import cn.mccreefei.technologystack.rpc.client.core.RpcRequestPool;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import cn.mccreefei.technologystack.rpc.support.transport.RpcResponse;
import com.alibaba.fastjson.util.TypeUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午3:29
 * 代理调用
 */
@Component
public class RpcInvoker implements InvocationHandler, MethodInterceptor {
    @Resource
    private RpcClient rpcClient;
    @Resource
    private RpcRequestPool requestPool;
    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        return doInvoke(method, parameters);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] parameters, MethodProxy proxy) throws Throwable {
       return doInvoke(method, parameters);
    }

    private Object doInvoke(Method method, Object[] parameters) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();

        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(requestId)
                .className(className)
                .methodName(methodName)
                .parameterTypes(parameterTypes)
                .parameters(parameters).build();
        rpcClient.send(rpcRequest);
        RpcResponse response = requestPool.getResponse(requestId);
        Object result = response.getResult();
        if (result == null){
            throw response.getException();
        }
        //fastjson会将对象内部的Object对象反序列化为Map形式，这里需要手动cast result类型
        if (result instanceof Map){
            result = TypeUtils.cast(result, method.getReturnType(), null);
        }
        return result;
    }
}
