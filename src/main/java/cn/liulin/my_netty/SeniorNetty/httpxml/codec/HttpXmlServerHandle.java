package cn.liulin.my_netty.SeniorNetty.httpxml.codec;

import cn.liulin.my_netty.SeniorNetty.httpxml.Address;
import cn.liulin.my_netty.SeniorNetty.httpxml.Order;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.List;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml.codec$
 *
 * @author ll
 * @date 2023-08-31 17:37:51
 **/
public class HttpXmlServerHandle extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlRequest httpXmlRequest) throws Exception {
        HttpRequest request = httpXmlRequest.getRequest();
        Order order = (Order) httpXmlRequest.getBody();
        System.out.println("Http server receive request : " + JSON.toJSONString(order));
        doBusiness(order);
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(new HttpXmlResponse(null, order));
        if (!HttpHeaders.isKeepAlive(request)) {
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    channelHandlerContext.close();
                }
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus internalServerError) {
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                internalServerError, Unpooled.copiedBuffer("失败 ： " + internalServerError.toString() + "\r\n", CharsetUtil.UTF_8));
        fullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    public void doBusiness(Order order) {
        order.getCustomer().setFirstName("狄仁杰");
        Address billTo = order.getBillTo();
        billTo.setCity("洛阳");
        order.setBillTo(billTo);
        order.setShipTo(billTo);
    }
}
