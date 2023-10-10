package cn.liulin.my_netty.NIO.netty.FixedLengthNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * cn.liulin.my_netty.NIO.netty.FixedLengthNetty$
 *
 * @author ll
 * @date 2023-10-09 16:45:58
 **/
public class FixedLengthFrameEncoder extends MessageToByteEncoder<ByteBuf> {

    private int length;

    public FixedLengthFrameEncoder(int length) {
        this.length = length;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        String msg = byteBuf.toString(CharsetUtil.UTF_8);
        if (msg.length() > length) {
            throw new UnsupportedOperationException(
                    "message length is too large, it's limited " + length);
        }

        // 如果长度不足，则进行补全
        if (msg.length() < length) {
            msg = addSpace(msg);
        }

        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer(msg.getBytes()));
    }

    /**
     * 进行空格补全
     * @author liulin
     * @date 2023-10-09 16:47:50
     * @param msg
     * @return java.lang.String
     **/
    private String addSpace(String msg) {
        StringBuilder builder = new StringBuilder(msg);
        for (int i = 0; i < length - msg.length(); i++) {
            builder.append(" ");
        }

        return builder.toString();
    }
}
