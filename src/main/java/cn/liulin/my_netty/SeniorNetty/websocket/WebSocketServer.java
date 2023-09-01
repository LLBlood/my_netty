package cn.liulin.my_netty.SeniorNetty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * cn.liulin.my_netty.SeniorNetty.websocket$
 *
 * @author ll
 * @date 2023-09-01 15:10:26
 **/
public class WebSocketServer {
    public void run(int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("handler", new WebSocketServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("WebSocket server started at port " + port + ".");
            System.out.println("Open your browser and navigate to http://localhost:" + port + "/");
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebSocketServer().run(8080);
    }
}
