package cn.liulin.my_netty.SeniorNetty.self;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 11:33:20
 **/
public class NettyClient {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();
    public void connect(int port, String host) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyMessageDecoder(1024*1024, 4, 4));
                            pipeline.addLast("MessageEncoder", new NettyMessageEncoder());
                            pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            pipeline.addLast("LoginAuthHandler", new LoginAuthReqHandler());
                            pipeline.addLast("HeartBeatHandler", new HeartBeatReqHandler());
                        }
                    });
            // 这部分代码尝试在给定的主机名（host）和端口号（port）上建立一个到远程主机的连接。同时，
            // 它也提供了一个本地地址（NettyConstant.LOCALIP和NettyConstant.LOCAL_PORT），
            // 用于指定本地网络接口和端口，用于接收从远程主机返回的数据。
            // 可以确保数据从远程主机返回时直接被接收，而不需要在多个网络接口之间进行转发。
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 所有资源释放完成后，重新连接
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try {
                            connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
    }
}
