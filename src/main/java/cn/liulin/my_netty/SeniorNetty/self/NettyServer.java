package cn.liulin.my_netty.SeniorNetty.self;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 11:47:16
 **/
public class NettyServer {
    public void bind() throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyMessageDecoder(1024*1024, 4, 4));
                        pipeline.addLast("MessageEncoder", new NettyMessageEncoder());
                        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                        pipeline.addLast("LoginAuthHandler", new LoginAuthRespHandler());
                        pipeline.addLast("HeartBeatHandler", new HeartBeatRespHandler());
                    }
                });
        serverBootstrap.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        System.out.println("Netty server start ok : " + NettyConstant.REMOTEIP + ":" + NettyConstant.PORT);
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }
}
