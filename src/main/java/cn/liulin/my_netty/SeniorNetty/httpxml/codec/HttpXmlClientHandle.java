package cn.liulin.my_netty.SeniorNetty.httpxml.codec;

import cn.liulin.my_netty.SeniorNetty.httpxml.OrderFactory;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml.codec$
 *
 * @author ll
 * @date 2023-08-31 17:06:16
 **/
public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest httpXmlRequest = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(httpXmlRequest);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlResponse httpXmlResponse) throws Exception {
        System.out.println("The client receive response of http header is : " + httpXmlResponse.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : " + JSON.toJSONString(httpXmlResponse.getResult()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
