package cn.mccreefei.technologystack.rpc.client.core;

import cn.mccreefei.technologystack.rpc.client.netty.RpcClientDecoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcClientEncoder;
import cn.mccreefei.technologystack.rpc.client.netty.RpcResponseHandler;
import cn.mccreefei.technologystack.rpc.support.serialization.Serialization;
import cn.mccreefei.technologystack.rpc.support.transport.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author MccreeFei
 * @create 2018-10-15 下午3:12
 */
@Component
@Slf4j
public class RpcClient implements BeanPostProcessor, DisposableBean{
    private Map<String, String> serviceAddressMap = new ConcurrentHashMap<>();
    private Map<String, ChannelHold> addressChannelMap = new ConcurrentHashMap<>();
    @Resource
    private RpcRequestPool rpcRequestPool;
    @Resource
    private Serialization serialization;
    @Resource
    private RpcResponseHandler rpcResponseHandler;
    private ServiceRecovery serviceRecovery = new ServiceRecovery(serviceAddressMap);


    public void send(RpcRequest request) {
        String serviceName = request.getClassName();
        String address = serviceAddressMap.get(serviceName);
        boolean isProvided = false;
        if (address != null){
            ChannelHold channelHold = addressChannelMap.get(address);
            if (channelHold != null){
                Channel channel = channelHold.getChannel();
                rpcRequestPool.addRequest(request.getRequestId(), channel.eventLoop());
                channel.writeAndFlush(request);
                isProvided = true;
            }
        }
        if (! isProvided){
            log.error("Service Server Not Provided! {}", serviceName);
        }
    }

    //创建Netty与Server的连接
    private void createNettyConnection(){
        try {
            serviceRecovery.recoverService();
        } catch (IOException | InterruptedException e) {
            log.error("service recover fail!", e);
            return;
        }
        Set<String> addressSet = serviceAddressMap.values().stream().distinct().collect(Collectors.toSet());
        if (StringUtils.isEmpty(addressSet)) {
            return;
        }
        for (String address : addressSet){
            String host = null;
            Integer port = null;
            try {
                String[] split = address.split(":");
                host = split[0];
                port = Integer.valueOf(split[1]);
            }catch (IndexOutOfBoundsException e){
                log.error("address [{}] invalid!", address);
                continue;
            }
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.channel(NioSocketChannel.class)
                    .group(eventLoopGroup)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RpcClientEncoder(serialization));
                            pipeline.addLast(new RpcClientDecoder(serialization));
                            pipeline.addLast(rpcResponseHandler);
                        }
                    });
            Channel channel = bootstrap.connect().channel();
            ChannelHold channelHold = new ChannelHold(channel, eventLoopGroup);
            addressChannelMap.put(address, channelHold);
        }

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        createNettyConnection();
        return bean;
    }

    @Override
    public void destroy() throws Exception {
        if (addressChannelMap != null){
            Collection<ChannelHold> channelHolds = addressChannelMap.values();
            if (!CollectionUtils.isEmpty(channelHolds)){
                channelHolds.forEach(channelHold -> {
                    channelHold.getChannel().closeFuture();
                    channelHold.getEventLoopGroup().shutdownGracefully();
                });
            }
        }
    }
}
