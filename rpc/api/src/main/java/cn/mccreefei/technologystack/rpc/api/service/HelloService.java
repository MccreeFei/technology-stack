package cn.mccreefei.technologystack.rpc.api.service;

import cn.mccreefei.technologystack.rpc.api.RpcProxy;

/**
 * @author MccreeFei
 * @create 2018-10-24 上午10:49
 */
@RpcProxy
public interface HelloService {
    public String sayHello(String content);
}
