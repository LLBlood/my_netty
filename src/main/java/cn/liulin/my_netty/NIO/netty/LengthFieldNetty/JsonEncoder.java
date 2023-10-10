package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 16:54:56
 **/
public class JsonEncoder extends MessageToByteEncoder<User> {

    @Override
    protected void encode(ChannelHandlerContext ctx, User user, ByteBuf buf)
            throws Exception {
        String json = JSON.toJSONString(user);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(json.getBytes()));
    }
}
