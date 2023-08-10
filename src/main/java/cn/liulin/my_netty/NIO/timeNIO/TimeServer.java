package cn.liulin.my_netty.NIO.timeNIO;

/**
 * cn.liulin.my_netty.NIO.timeNIO$
 *
 * @author ll
 * @date 2023-08-09 15:33:05
 **/
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer, "NIO-multiplexerTimeServer-001").start();
    }
}
