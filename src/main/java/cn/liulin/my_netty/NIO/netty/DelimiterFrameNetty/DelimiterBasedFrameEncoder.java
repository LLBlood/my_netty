package cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty$
 *
 * @author ll
 * @date 2023-10-10 15:59:15
 **/
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer((s + delimiter).getBytes()));
    }
}
