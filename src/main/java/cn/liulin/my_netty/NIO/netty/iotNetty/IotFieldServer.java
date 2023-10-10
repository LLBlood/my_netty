package cn.liulin.my_netty.NIO.netty.iotNetty;

import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.EchoServerHandler;
import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.JsonDecoder;
import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.JsonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * cn.liulin.my_netty.NIO.netty.iotNetty$
 *
 * @author ll
 * @date 2023-10-10 17:10:43
 **/
public class IotFieldServer {
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
                            socketChannel.pipeline().addLast(new ControllerFrameDecoder());
                            socketChannel.pipeline().addLast(new ByteArrayToStringDecoder());
                            socketChannel.pipeline().addLast(new ControllerFrameEncoder());
                            socketChannel.pipeline().addLast(new IotFieldServerHandler());
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
