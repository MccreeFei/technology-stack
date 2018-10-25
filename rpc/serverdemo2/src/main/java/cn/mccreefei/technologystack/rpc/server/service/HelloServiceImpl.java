package cn.mccreefei.technologystack.rpc.server.service;

import cn.mccreefei.technologystack.rpc.api.RpcService;
import cn.mccreefei.technologystack.rpc.api.service.HelloService;

/**
 * @author MccreeFei
 * @create 2018-10-25 上午9:46
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String content) {
        return "hello, " + content + "!";
    }
}
