package cn.liulin.my_netty.Netty.introduction;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * cn.liulin.my_netty.Netty.introduction$
 *
 * @author ll
 * @date 2023-08-10 16:42:43
 **/
public class TimeServer {
    public void bind(int port) throws InterruptedException {
        // 线程组，包含一组NIO线程，用于网络事件的处理，实际是Reactor线程组
        // 用于服务端接收客户端的连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // SocketChannel的网络读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // backlog参数主要用于控制服务器端接受连接的最大数量
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandle());
            // sync 同步阻塞方法等待绑定操作完成
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 等待服务端链路关闭之后Main函数才退出
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class ChildChannelHandle extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new TimeServer().bind(port);
    }


}
