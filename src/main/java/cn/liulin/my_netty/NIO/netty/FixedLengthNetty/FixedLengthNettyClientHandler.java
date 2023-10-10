package cn.liulin.my_netty.NIO.netty.FixedLengthNetty;

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
public class FixedLengthNettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已准备发送数据");
        ctx.writeAndFlush(Unpooled.copiedBuffer("test1, test2, test3, test4, test5".toCharArray(), CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        String s = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("已收到服务端返回的消息===>" + s);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
