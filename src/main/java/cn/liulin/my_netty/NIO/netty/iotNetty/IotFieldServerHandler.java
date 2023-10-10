package cn.liulin.my_netty.NIO.netty.iotNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.liulin.my_netty.NIO.netty.iotNetty$
 *
 * @author ll
 * @date 2023-10-10 17:17:01
 **/
public class IotFieldServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        byte[] content = (byte[]) msg;
//        String tempContent = new String(content);
//        System.out.println("receive client msg ：" + tempContent);
        System.out.println("receive client msg ：" + msg);
        ctx.writeAndFlush(msg);
    }
}
