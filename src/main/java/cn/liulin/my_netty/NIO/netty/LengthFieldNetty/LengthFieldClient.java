package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty.DelimiterBasedFrameClientHandler;
import cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty.DelimiterBasedFrameEncoder;
import cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty.DelimiterBasedFrameServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 16:46:22
 **/
public class LengthFieldClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 12356))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                            // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                            // 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
                            socketChannel.pipeline().addLast(new JsonDecoder());
                            // 对响应数据进行编码，主要是将User对象序列化为json
                            socketChannel.pipeline().addLast(new JsonEncoder());
                            // 处理客户端的请求的数据，并且进行响应
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect().sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
