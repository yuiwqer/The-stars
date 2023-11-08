package com.example.demo.netty;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.RoomService;
import com.example.demo.service.SendService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class NettyServer {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SendService sendService;
    @Value("${server.port}")
    public int port;

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    channelRepository.baseMap.add(ctx.channel());
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) {
                                    channelRepository.baseMap.remove(ctx.channel());
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    System.out.println(ctx.channel().id() + "网络异常");
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
                                    //接受消息并处理
                                    String type="sign_up";
                                    String username="123";
                                    String password="123";
                                    String msg="666";
                                    String name="世界";
                                    switch (type){
                                        case "sign_up":
                                            loginService.register(username,password);
                                            break;
                                        case "sign_in":
                                            loginService.login(username,password,channelHandlerContext.channel());
                                            break;
                                        case "sign_out":
                                            loginService.logout(username,channelHandlerContext.channel());
                                            break;
                                        case "chat_room_join":
                                            roomService.roomEnter(username,channelHandlerContext.channel());
                                            break;
                                        case "chat_room_exit":
                                            roomService.roomExit(username,channelHandlerContext.channel());
                                            break;
                                        case "chat_room_send":
                                            roomService.roomSend(username,name,msg,channelHandlerContext.channel());
                                            break;

                                    }

                                }
                            });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            log.info("netty server start in "+port);
            b.bind(port).sync().channel().closeFuture().sync();
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
