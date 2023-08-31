package cn.liulin.my_netty.SeniorNetty.httpxml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml.codec$
 *
 * @author ll
 * @date 2023-08-31 16:53:31
 **/
public class HttpXmlResponseEncoder extends AbstractHttpXmlEnCoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpXmlResponse httpXmlResponse, List<Object> list) throws Exception {
        ByteBuf byteBuf = encode0(channelHandlerContext, httpXmlResponse.getResult());
        FullHttpResponse httpResponse = httpXmlResponse.getHttpResponse();
        if (httpResponse == null) {
            httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        } else {
            httpResponse = new DefaultFullHttpResponse(httpXmlResponse.getHttpResponse().getProtocolVersion(),
                    httpXmlResponse.getHttpResponse().getStatus(), byteBuf);
        }
        httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/xml");
        HttpHeaders.setContentLength(httpResponse, byteBuf.readableBytes());
        list.add(httpResponse);
    }
}
