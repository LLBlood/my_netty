package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 17:03:12
 **/
public class EchoClientHandler extends SimpleChannelInboundHandler<User> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.write(getUser());
        }
    }

    private User getUser() {
        User user = new User();
        user.setAge(27);
        user.setName("zhangxufeng");
        return user;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User user) throws Exception {
        System.out.println("receive message from server: " + user);
    }
}
