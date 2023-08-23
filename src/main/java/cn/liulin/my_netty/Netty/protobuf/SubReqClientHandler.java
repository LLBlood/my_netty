package cn.liulin.my_netty.Netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * cn.liulin.my_netty.Netty.protobuf$
 *
 * @author ll
 * @date 2023-08-23 16:11:22
 **/
public class SubReqClientHandler extends ChannelHandlerAdapter {
    public SubReqClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 1000; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    public SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("Lilingfeng");
        builder.setProductName("Netty book for protobuf");
        List<String> addressList = new ArrayList<>(3);
        addressList.add("si chuan cheng du");
        addressList.add("shan xi cheng ding");
        addressList.add("shan xi an kang");
        builder.addAllAddress(addressList);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
