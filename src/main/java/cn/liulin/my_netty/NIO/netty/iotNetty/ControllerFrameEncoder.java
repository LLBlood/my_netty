package cn.liulin.my_netty.NIO.netty.iotNetty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class ControllerFrameEncoder extends MessageToByteEncoder<String>{
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] msgByte = msg.getBytes(StandardCharsets.UTF_8);
		out.writeShort((short)0xffff);
		out.writeInt(msgByte.length);
//		byte[] newData = new byte[3];
//		System.arraycopy(msgByte,0, newData, 0, 3);
//		out.writeBytes(newData);
//		ctx.writeAndFlush(out);
		out.writeBytes(msgByte);
//		ctx.writeAndFlush(out);
	}
}
