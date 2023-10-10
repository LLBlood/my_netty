package cn.liulin.my_netty.NIO.netty.iotNetty;

import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.User;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * cn.liulin.my_netty.NIO.netty.iotNetty$
 *
 * @author ll
 * @date 2023-10-10 17:42:55
 **/
public class ByteArrayToStringDecoder extends MessageToMessageDecoder<byte[]> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, byte[] bytes, List<Object> list) throws Exception {
        list.add(new String(bytes));
    }
}
