package cn.mccreefei.technologystack.rpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MccreeFei
 * @create 2018-10-11 下午5:02
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.mccreefei.technologystack.rpc")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
