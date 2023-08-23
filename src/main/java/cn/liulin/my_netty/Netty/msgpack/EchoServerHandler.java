package cn.liulin.my_netty.Netty.msgpack;

import cn.liulin.my_netty.Netty.javaSer.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * cn.liulin.my_netty.Netty.msgpack$
 *
 * @author ll
 * @date 2023-08-15 16:03:40
 **/
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server receive the msgpack message : " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("`2222222222222222222");
        cause.printStackTrace();
    }
}
