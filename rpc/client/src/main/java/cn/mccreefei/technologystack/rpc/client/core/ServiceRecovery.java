package cn.mccreefei.technologystack.rpc.client.core;

import cn.mccreefei.technologystack.rpc.api.RpcProxy;
import cn.mccreefei.technologystack.rpc.client.netty.RpcClientDecoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcClientEncoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcResponseHandler;
import cn.mccreefei.technologystack.rpc.support.config.ConfigUtil;
import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author MccreeFei
 * @create 2018-10-24 下午2:09
 */
@Slf4j
public class ServiceRecovery {
    private Map<String, String> serviceAddressMap = new ConcurrentHashMap<>();
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private final String rootPath = ConfigUtil.ROOT_PATH;

    public ServiceRecovery(Map<String, String> serviceAddressMap){
        this.serviceAddressMap = serviceAddressMap;
    }


    //连接zookeeper
    private void connect() throws IOException, InterruptedException {
        String zookeeperAddress = ConfigUtil.ADDRESS;
        zooKeeper = new ZooKeeper(zookeeperAddress, ConfigUtil.SESSION_TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
    }

    //发现服务对应的地址
    public void recoverService() throws IOException, InterruptedException {
        connect();
        Reflections reflections = new Reflections("cn.mccreefei.technologystack.rpc.api");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcProxy.class);
        Set<String> services = typesAnnotatedWith.stream().map(cls -> cls.getName()).collect(Collectors.toSet());
        services.forEach(serviceName -> {
            try {
                String servicePath = rootPath + "/" + serviceName;
                if (zooKeeper.exists(servicePath, false) != null){
                    List<String> addressChildren = zooKeeper.getChildren(servicePath, false);
                    if (!StringUtils.isEmpty(addressChildren)){
                        //地址多于一个取第一个，可以扩展做负载均衡
                        byte[] bytes = zooKeeper.getData(servicePath + "/" + addressChildren.get(0), false, null);
                        String address = new String(bytes);
                        serviceAddressMap.put(serviceName, address);
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                log.error("", e);
            }
        });
    }



}
