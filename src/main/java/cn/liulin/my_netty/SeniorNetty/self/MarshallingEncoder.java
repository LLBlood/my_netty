package cn.liulin.my_netty.SeniorNetty.self;

import cn.liulin.my_netty.Netty.jbossmarshalling.MarshallingCodeCFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 09:52:07
 **/
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        this.marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    public void encode(Object msg, ByteBuf out) throws IOException {
        try {
            // 获取输出流（out）的当前索引（writerIndex），并将其存储在lengthPos变量中。这个索引稍后将被用于替换长度字段。
            int lengthPos = out.writerIndex();
            // 将长度占位符内容写入输出流
            out.writeBytes(LENGTH_PLACEHOLDER);
            // 将实际得数据内容写入到输出流中
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            // 输出流在lengthPos写入实际的消息长度，需要排除长度占位符的内容，并写成实际的消息长度
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
