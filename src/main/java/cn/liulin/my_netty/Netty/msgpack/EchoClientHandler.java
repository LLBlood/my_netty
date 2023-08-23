package cn.liulin.my_netty.Netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.Netty.msgpack$
 *
 * @author ll
 * @date 2023-08-15 16:03:40
 **/
public class EchoClientHandler extends ChannelHandlerAdapter {
    int sendNumber;
    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = userInfos();
        for (UserInfo userInfo : userInfos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    private UserInfo[] userInfos() {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo();
            userInfo.setUserName("ABCDEFG------>" + i);
            userInfo.setUserId(i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive the msgpack message : " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("`1111111111111111111");
        cause.printStackTrace();
    }
}
