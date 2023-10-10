package cn.liulin.my_netty.NIO.netty.FixedStringLengthNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.FixedLengthNetty$
 *
 * @author ll
 * @date 2023-10-09 16:45:58
 **/
public class FixedLengthFrameByteArrayEncoder extends MessageToByteEncoder<byte[]> {

    private int length;

    public FixedLengthFrameByteArrayEncoder(int length) {
        this.length = length;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, byte[] bytes, ByteBuf byteBuf2) throws Exception {
        if (bytes.length > length) {
            throw new UnsupportedOperationException(
                    "message length is too large, it's limited " + length);
        }

        // 如果长度不足，则进行补全
        if (bytes.length < length) {
            bytes = addSpace(bytes);
        }

        channelHandlerContext.writeAndFlush(bytes);
    }

    /**
     * 进行空格补全
     * @author liulin
     * @date 2023-10-09 16:47:50
     * @param msg
     * @return java.lang.String
     **/
    private byte[] addSpace(byte[] msg) {
        byte[] newData = new byte[length];
        System.arraycopy(msg, 0, newData, 0, msg.length);
        for (int i = msg.length; i < newData.length; i++) {
            // ASCII value for space
            newData[i] = (byte) 32;
        }
        return newData;
    }
}
