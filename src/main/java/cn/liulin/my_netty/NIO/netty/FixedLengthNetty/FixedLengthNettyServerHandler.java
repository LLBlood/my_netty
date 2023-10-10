package cn.liulin.my_netty.NIO.netty.FixedLengthNetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * cn.liulin.my_netty.NIO.netty.FixedLengthNetty$
 *
 * @author ll
 * @date 2023-10-09 15:42:48
 **/
public class FixedLengthNettyServerHandler extends ChannelInboundHandlerAdapter {

    int i = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println(this);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到客户端发来的消息===>" + byteBuf.toString(CharsetUtil.UTF_8) + ++i);
        ctx.writeAndFlush(msg);
    }
}
