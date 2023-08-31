package cn.liulin.my_netty.SeniorNetty.httpxml.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml.codec$
 *
 * @author ll
 * @date 2023-08-31 16:56:46
 **/
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {
    protected HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    protected HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DefaultFullHttpResponse defaultFullHttpResponse, List<Object> list) throws Exception {
        HttpXmlResponse httpXmlResponse = new HttpXmlResponse(defaultFullHttpResponse, decode0(channelHandlerContext, defaultFullHttpResponse.content()));
        list.add(httpXmlResponse);
    }
}
