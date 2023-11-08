package com.example.demo.service;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.LoginUserRepository;
import com.example.demo.util.R;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private LoginUserRepository loginUserRepository;
    @Autowired
    private ChannelRepository channelRepository;
    public String roomEnter(String username,Channel channel){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        if(loginUser!=null&&loginUser.getId()!=0){
            channelRepository.chatRoomMap.get("世界").add(channel);
        }
        return R.ok(200,"加入聊天房间!",null);
    }
    public String roomExit(String username,Channel channel){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        if(loginUser!=null&&loginUser.getId()!=0){
            channelRepository.chatRoomMap.get("世界").remove(channel);
        }
        return R.ok(200,"退出聊天房间!",null);
    }
    public String roomSend(String username,String name,String msg,Channel channel){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        if(loginUser!=null&&loginUser.getId()!=0){
            channelRepository.chatRoomMap.get(name).writeAndFlush(msg);
        }
        return R.ok(200,"发送聊天消息!",null);
    }
}
