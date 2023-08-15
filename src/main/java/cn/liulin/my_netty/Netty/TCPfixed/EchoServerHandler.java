package cn.liulin.my_netty.Netty.TCPfixed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.Netty.TCPdelimiter$
 *
 * @author ll
 * @date 2023-08-15 14:08:57
 **/
public class EchoServerHandler extends ChannelHandlerAdapter {
    int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is " +  ++count + " times receive client : [" + body + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
