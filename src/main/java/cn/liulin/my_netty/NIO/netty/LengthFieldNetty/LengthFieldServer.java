package cn.liulin.my_netty.NIO.netty.LengthFieldNetty;

import cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty.DelimiterBasedFrameEncoder;
import cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty.DelimiterBasedFrameServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.LengthFieldNetty$
 *
 * @author ll
 * @date 2023-10-10 16:46:16
 **/
public class LengthFieldServer {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(12356)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 这里将LengthFieldBasedFrameDecoder添加到pipeline的首位，因为其需要对接收到的数据
                            // 进行长度字段解码，这里也会对数据进行粘包和拆包处理
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                            // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                            // 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
                            socketChannel.pipeline().addLast(new JsonDecoder());
                            // 对响应数据进行编码，主要是将User对象序列化为json
                            socketChannel.pipeline().addLast(new JsonEncoder());
                            // 处理客户端的请求的数据，并且进行响应
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind().sync();
            System.out.println("服务端准备接收消息");
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
