package cn.mccreefei.technologystack.rpc.client.core;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-10-24 下午2:36
 */
@Data
public class ChannelHold {
    private Channel channel;
    private EventLoopGroup eventLoopGroup;
    public ChannelHold(Channel channel, EventLoopGroup eventLoopGroup){
        this.channel = channel;
        this.eventLoopGroup = eventLoopGroup;
    }
}
