package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.LoginUserRepository;
import com.example.demo.util.R;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginUserRepository loginUserRepository;
    @Autowired
    private ChannelRepository channelRepository;
    public String register(String username, String password){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        if(loginUser!=null&&loginUser.getId()!=0){
            LoginUser save=new LoginUser();
            save.setUsername(username);
            save=loginUserRepository.save(save);
            save.setPassword("");
            return R.ok(200,"注册成功",save);
        }
        return R.msg(500,"账号已存在");
    }
    public String login(String username,String password,Channel channel){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        if(loginUser.getPassword().equals(password)){
            //进入在线列表
            channelRepository.loginMap.add(channel);
            return R.ok(200,"登录成功","");
        }
        return "";
    }
    public String logout(String username,Channel channel){
        LoginUser loginUser=loginUserRepository.findByUsername(username);
        channelRepository.loginMap.remove(channel);
        return R.ok(200,"登出成功",null);
    }
}
