package cn.liulin.my_netty.NIO.netty.iotNetty;

import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.EchoClientHandler;
import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.JsonDecoder;
import cn.liulin.my_netty.NIO.netty.LengthFieldNetty.JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetSocketAddress;

/**
 * cn.liulin.my_netty.NIO.netty.iotNetty$
 *
 * @author ll
 * @date 2023-10-10 17:10:50
 **/
public class IotFieldClient {
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
                            socketChannel.pipeline().addLast(new ControllerFrameDecoder());
                            socketChannel.pipeline().addLast(new ControllerFrameEncoder());
                            socketChannel.pipeline().addLast(new IotFieldClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect().sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
