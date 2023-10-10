package cn.liulin.my_netty.NIO.netty.iotNetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ControllerFrameDecoder extends ByteToMessageDecoder {

	//2字节，头 0xff 0xff
	//4字节，数据长度，网络字节序，n
	//n字节，数据

	public static final int MIN_HEAD_LENGTHs = 6;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < MIN_HEAD_LENGTHs) {
			return ;
		}
		in.markReaderIndex();
		int startIndex = in.readShort();
		int pktLength;
		if(startIndex != (byte) 0xffff) {
			ctx.close();
			return ;
		}
		pktLength = in.readInt();
		
		if(in.readableBytes()<pktLength) {
			//剩余长度不足
			in.resetReaderIndex();
			return ;
		}
		byte[] body = new byte[pktLength];
		in.readBytes(body);

		out.add(body);
	}
}
