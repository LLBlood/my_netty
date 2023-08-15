package cn.liulin.my_netty.Netty.introduction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * cn.liulin.my_netty.Netty.introduction$
 * 对网络事件进行读写操作
 * @author ll
 * @date 2023-08-15 10:45:40
 **/
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("The timeServer receive order : " + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
        // 用于创建一个新的ByteBuf，该ByteBuf是给定值的拷贝
        ByteBuf byteBuf = Unpooled.copiedBuffer(currentTime.getBytes(StandardCharsets.UTF_8));
        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 从性能角度，防止频繁的唤醒Selector进行消息发送，Netty的write方法并不直接将消息写入SocketChannel中，
        // 把待发送的消息发送到缓冲数组中，flush将缓冲数组的消息全部写入到SocketChannel
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
