package cn.mccreefei.technologystack.rpc.server.service;

import cn.mccreefei.technologystack.rpc.support.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author MccreeFei
 * @create 2018-10-15 下午1:17
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String content) {
        return "hello, " + content + "!";
    }
}
