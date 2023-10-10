package cn.liulin.my_netty.NIO.netty.FixedStringLengthNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.NIO.netty.FixedLengthNetty$
 *
 * @author ll
 * @date 2023-10-09 15:40:39
 **/
public class FixedLengthNettyStringServer {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(16545)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(70));
                            socketChannel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                            socketChannel.pipeline().addLast(new FixedLengthFrameStringEncoder(70));
                            socketChannel.pipeline().addLast(new FixedLengthNettyServerStringHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind().sync();
            System.out.println("服务端准备等待连接");
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
