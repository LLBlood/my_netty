package cn.liulin.my_netty.SeniorNetty.self;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 09:46:39
 **/
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, ByteBuf byteBuf) throws Exception {
        if (nettyMessage == null || nettyMessage.getHeader() == null) {
            throw new Exception("The encoder message is null");
        }
        byteBuf.writeInt(nettyMessage.getHeader().getCrcCode());
        byteBuf.writeInt(nettyMessage.getHeader().getLength());
        byteBuf.writeLong(nettyMessage.getHeader().getSessionId());
        byteBuf.writeByte(nettyMessage.getHeader().getType());
        byteBuf.writeByte(nettyMessage.getHeader().getPriority());
        byteBuf.writeInt(nettyMessage.getHeader().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> stringObjectEntry : nettyMessage.getHeader().getAttachment().entrySet()) {
            key = stringObjectEntry.getKey();
            keyArray = key.getBytes(StandardCharsets.UTF_8);
            byteBuf.writeInt(keyArray.length);
            byteBuf.writeBytes(keyArray);
            value = stringObjectEntry.getValue();
            marshallingEncoder.encode(value, byteBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if (nettyMessage.getBody() != null) {
            marshallingEncoder.encode(nettyMessage.getBody(), byteBuf);
        } else {
            byteBuf.writeInt(0);
        }
        byteBuf.setInt(4, byteBuf.readableBytes() - 8);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
