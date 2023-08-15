package cn.liulin.my_netty.Netty.TCPpacket;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.Netty.TCPpacket$
 *
 * @author ll
 * @date 2023-08-15 11:45:36
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {
    int count;
    byte[] req;
    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);
    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = null;
        for (int i = 0; i < 100; i++) {
            byteBuf = Unpooled.buffer(req.length);
            byteBuf.writeBytes(req);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Now is : " + body + "; count : " + ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
