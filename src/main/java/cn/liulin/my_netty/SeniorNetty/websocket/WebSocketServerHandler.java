package cn.liulin.my_netty.SeniorNetty.websocket;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * cn.liulin.my_netty.SeniorNetty.websocket$
 *
 * @author ll
 * @date 2023-09-01 15:19:09
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // 传统的HTTP接入
        if (o instanceof FullHttpRequest) {
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) o);
        } else if (o instanceof WebSocketFrame) {
            // WebSocket接入
            handleWebSocketFrame(channelHandlerContext, (WebSocketFrame) o);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame o) {
        // 判断是不是关闭链路的命令
        if (o instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(channelHandlerContext.channel(), (CloseWebSocketFrame) o.retain());
            return;
        }
        // 判断是否为Ping消息
        if (o instanceof PingWebSocketFrame) {
            channelHandlerContext.channel().write(new PongWebSocketFrame(o.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(o instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", o.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) o).text();
        logger.error(String.format("%s received %s", channelHandlerContext.channel(), request));
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(o + ", 欢迎使用Netty WebSocket服务，现在时刻：" + new Date().toString()));
    }

    private void handleHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest o) {
        // 如果HTTP解码失败，返回HTTP异常
        if (!o.getDecoderResult().isSuccess() ||
                (!"websocket".equals(o.headers().get("Upgrade")))) {
            sendHttpResponse(channelHandlerContext, o, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        webSocketServerHandshaker = wsFactory.newHandshaker(o);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(channelHandlerContext.channel());
        } else {
            webSocketServerHandshaker.handshake(channelHandlerContext.channel(), o);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(byteBuf);
            byteBuf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture channelFuture = channelHandlerContext.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
