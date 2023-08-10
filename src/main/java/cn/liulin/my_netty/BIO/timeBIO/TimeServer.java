package cn.liulin.my_netty.BIO.timeBIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * cn.liulin.my_netty.BIO.timeBIO$
 *
 * @author ll
 * @date 2023-08-09 14:38:15
 **/
public class TimeServer {

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TIME SERVER START PORT : " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                threadPoolExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (serverSocket != null) {
                System.out.println("time server close");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
