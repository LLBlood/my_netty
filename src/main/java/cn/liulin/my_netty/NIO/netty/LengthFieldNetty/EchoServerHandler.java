package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 17:01:46
 **/
public class EchoServerHandler extends SimpleChannelInboundHandler<User> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User user) throws Exception {
        System.out.println("receive from client: " + user);
        ctx.write(user);
    }
}
