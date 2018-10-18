package cn.mccreefei.technologystack.rpc.server;

import cn.mccreefei.technologystack.rpc.server.netty.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @author MccreeFei
 * @create 2018-10-12 下午5:07
 * server 启动器 伴随SpringBoot启动
 */
@Component
@Slf4j
public class RpcServerRunner implements CommandLineRunner {
    @Value("${netty.port}")
    private int port;
    @Resource
    private ServerInitializer serverInitializer;
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    @Override
    public void run(String... args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .localAddress(new InetSocketAddress(port))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(serverInitializer);
        try {
            serverBootstrap.bind().sync().channel().closeFuture().sync();
        }catch (Exception e){
            log.error("", e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
