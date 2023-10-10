package cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty$
 *
 * @author ll
 * @date 2023-10-10 15:59:15
 **/
public class DelimiterBasedFrameClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("收到服务端返回的消息===>" + s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已准备发送数据");
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush("test1, test2, test3, test4, test5你好吗");
        }
    }
}
