package cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty$
 *
 * @author ll
 * @date 2023-10-10 15:59:15
 **/
public class DelimiterBasedFrameServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端消息===>" + msg);
        ctx.writeAndFlush(msg);
    }
}
