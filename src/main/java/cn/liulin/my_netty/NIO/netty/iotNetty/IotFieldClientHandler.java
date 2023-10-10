package cn.liulin.my_netty.NIO.netty.iotNetty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.iotNetty$
 *
 * @author ll
 * @date 2023-10-10 17:17:01
 **/
public class IotFieldClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive server msg ：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端准备发送数据");
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush("你就是个大傻瓜");
        }
    }
}
