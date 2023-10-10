package cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.DelimiterFrameNetty$
 *
 * @author ll
 * @date 2023-10-10 15:59:15
 **/
public class DelimiterBasedFrameServer {

    public static String delimiter = "_$";

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
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                                    Unpooled.wrappedBuffer(delimiter.getBytes(StandardCharsets.UTF_8))));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameServerHandler());
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
