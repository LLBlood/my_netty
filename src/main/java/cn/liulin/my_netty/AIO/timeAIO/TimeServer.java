package cn.liulin.my_netty.AIO.timeAIO;

/**
 * cn.liulin.my_netty.AIO.timeAIO$
 *
 * @author ll
 * @date 2023-08-09 17:45:35
 **/
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new AsyncTimeServerHandler(port), "AIO-AsyncTimeServerHandler-001").start();
    }
}
