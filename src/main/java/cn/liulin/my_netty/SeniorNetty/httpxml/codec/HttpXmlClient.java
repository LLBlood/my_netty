package cn.liulin.my_netty.SeniorNetty.httpxml.codec;

import cn.liulin.my_netty.SeniorNetty.httpxml.Order;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml.codec$
 *
 * @author ll
 * @date 2023-08-31 17:00:28
 **/
public class HttpXmlClient {
    public void connect(int port) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("http-decoder", new HttpResponseDecoder());
                            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            socketChannel.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));
                            socketChannel.pipeline().addLast("http-encoder", new HttpRequestEncoder());
                            socketChannel.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());
                            socketChannel.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandle());
                        }
                    });
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress(port)).sync();
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpXmlClient().connect(8080);
    }
}
