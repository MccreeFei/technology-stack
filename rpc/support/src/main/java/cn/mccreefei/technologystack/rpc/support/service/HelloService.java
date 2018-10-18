package cn.mccreefei.technologystack.rpc.support.service;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午1:54
 */
@RpcService(proxyTargetClass = true)
public interface HelloService {
    public String sayHello(String content);
}
