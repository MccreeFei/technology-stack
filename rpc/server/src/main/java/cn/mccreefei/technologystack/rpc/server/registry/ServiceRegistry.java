package cn.mccreefei.technologystack.rpc.server.registry;

import cn.mccreefei.technologystack.rpc.api.RpcService;
import cn.mccreefei.technologystack.rpc.support.config.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author MccreeFei
 * @create 2018-10-24 上午10:17
 */
@Component
@Slf4j
public class ServiceRegistry implements ApplicationContextAware{
    private ZooKeeper zookeeper;
    private String rootPath = ConfigUtil.ROOT_PATH;
    @Value("${netty.host}")
    private String serverHost;
    @Value("${netty.port}")
    private int serverPort;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private void connect() throws IOException {
        String address = ConfigUtil.ADDRESS;
        Integer sessionTimeOut = ConfigUtil.SESSION_TIME_OUT;
        zookeeper = new ZooKeeper(address, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
                    countDownLatch.countDown();
                }
            }
        });
    }

    private void createRootPath() {
        try {
            Stat stat = zookeeper.exists(rootPath, false);
            if (stat == null){
                zookeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    private void createServiceNode(String serviceName){
        try {
            String servicePath = rootPath + "/" + serviceName;
            Stat stat = zookeeper.exists(servicePath, false);
            if (stat == null){
                zookeeper.create(servicePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    private void createServiceAddressNode(String serviceName){
        createServiceNode(serviceName);
        String serverAddress = serverHost + ":" + serverPort;
        String serviceAddressPath = rootPath + "/" + serviceName + "/" + serverAddress;
        try {
            zookeeper.create(serviceAddressPath, serverAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //连接zookeeper
        try {
            connect();
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            log.error("", e);
        }
        //创建根节点路径
        createRootPath();
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(RpcService.class);
        if (!CollectionUtils.isEmpty(beansWithAnnotation)){
            beansWithAnnotation.values().forEach(serviceBean -> {
                String serviceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                log.info("register @RpcService : " + serviceName);
                createServiceAddressNode(serviceName);
            });
        }
    }
}
