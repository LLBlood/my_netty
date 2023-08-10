package cn.liulin.my_netty.NIO.timeNIO;

/**
 * cn.liulin.my_netty.NIO.timeNIO$
 *
 * @author ll
 * @date 2023-08-09 16:20:27
 **/
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new TimeClientHandle("127.0.0.1", port)).start();
    }
}
