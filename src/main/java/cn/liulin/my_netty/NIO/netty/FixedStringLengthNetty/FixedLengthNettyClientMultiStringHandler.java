package cn.liulin.my_netty.NIO.netty.FixedStringLengthNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * cn.liulin.my_netty.NIO.netty.FixedLengthNetty$
 *
 * @author ll
 * @date 2023-10-09 15:42:48
 **/
public class FixedLengthNettyClientMultiStringHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已准备发送数据");
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush("test1, test2, test3, test4, test5你好吗");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("已收到服务端返回的消息===>" + msg);
    }
}
