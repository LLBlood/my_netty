package cn.liulin.my_netty.AIO.timeAIO;

/**
 * cn.liulin.my_netty.AIO.timeAIO$
 *
 * @author ll
 * @date 2023-08-10 15:46:31
 **/
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}
