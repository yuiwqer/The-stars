package com.example.demo.repository;

import com.example.demo.netty.NettyServer;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ChannelRepository {
    public ChannelGroup baseMap = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public ChannelGroup loginMap = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public Map<String, ChannelGroup> chatRoomMap = new HashMap<>();
    @PostConstruct
    public void roomInit(){
        chatRoomMap.put("世界",new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

}
