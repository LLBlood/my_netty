package cn.liulin.my_netty.Netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * cn.liulin.my_netty.Netty.msgpack$
 *
 * @author ll
 * @date 2023-08-15 15:52:34
 **/
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(o);
        byteBuf.writeBytes(write);
    }
}
