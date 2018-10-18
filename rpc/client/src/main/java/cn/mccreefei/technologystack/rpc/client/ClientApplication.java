package cn.mccreefei.technologystack.rpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MccreeFei
 * @create 2018-10-11 下午4:45
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.mccreefei.technologystack.rpc")
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
