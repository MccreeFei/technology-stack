package cn.mccreefei.technologystack.rpc.client.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;

/**
 * @author MccreeFei
 * @create 2018-10-16 下午3:23
 * 代理工厂 提供jdk动态代理和Cglib代理方式
 */
@Component
@Slf4j
public class RpcProxyFactory {
    @Resource
    private RpcInvoker rpcInvoker;

    public <T> T createInstance(Class<T> interfaceClass){
        return createInstance(interfaceClass, false);
    }

    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> cls, boolean isTargetClass){
        if (isTargetClass){
            log.info("use cglib : " + cls.getSimpleName());
            Enhancer enhancer = new Enhancer();
            enhancer.setCallback(rpcInvoker);
            enhancer.setSuperclass(cls);
            return (T) enhancer.create();
        }else {
            log.info("use jdk dynamic proxy : " + cls.getSimpleName());
            return (T) Proxy.newProxyInstance(cls.getClassLoader(),
                    new Class[]{cls}, rpcInvoker);
        }
    }
}
