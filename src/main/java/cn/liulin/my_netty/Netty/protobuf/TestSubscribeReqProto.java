package cn.liulin.my_netty.Netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * cn.liulin.my_netty.Netty.protobuf$
 *
 * @author ll
 * @date 2023-08-23 14:27:38
 **/
public class TestSubscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] req) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(req);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("wang wu");
        builder.setProductName("a si di");
        List<String> addressList = new ArrayList<>(3);
        addressList.add("si chuan cheng du");
        addressList.add("shan xi cheng ding");
        addressList.add("shan xi an kang");
        builder.addAllAddress(addressList);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq subscribeReq = createSubscribeReq();
        System.out.println("Before encode : " + subscribeReq.toString());
        SubscribeReqProto.SubscribeReq decode = decode(encode(subscribeReq));
        System.out.println("After decode : " + decode.toString());
        System.out.println("Assert equal : -->" + subscribeReq.equals(decode));
    }
}
