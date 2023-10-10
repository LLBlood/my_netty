package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 16:54:48
 **/
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out)
            throws Exception {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        User user = JSON.parseObject(new String(bytes, CharsetUtil.UTF_8), User.class);
        out.add(user);
    }
}