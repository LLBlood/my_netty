package cn.liulin.my_netty.SeniorNetty.self;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 11:28:45
 **/
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;
        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("Receive client heart beat message : ---> " + JSON.toJSONString(nettyMessage));
            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client : ---> " + JSON.toJSONString(heartBeat));
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
