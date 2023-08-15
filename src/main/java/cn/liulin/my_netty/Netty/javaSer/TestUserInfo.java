package cn.liulin.my_netty.Netty.javaSer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * cn.liulin.my_netty.Netty.javaSer$
 *
 * @author ll
 * @date 2023-08-15 15:00:56
 **/
public class TestUserInfo {
    public static void main(String[] args) throws IOException {
//        testSer();
        testSpeed();
    }

    private static void testSpeed() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUserName("Netty");
        int loop = 1000000;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userInfo);
            objectOutputStream.flush();
            objectOutputStream.close();
            byte[] b = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("jdk se speed : " + (endTime - startTime) + "ms");
        System.out.println("----------------------------");
        startTime = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < loop; i++) {
            byte[] bytes = userInfo.codeC(byteBuffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("ByteBuffer se speed : " + (endTime - startTime) + "ms");
    }

    private static void testSer() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUserName("Netty");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(userInfo);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] b = byteArrayOutputStream.toByteArray();
        System.out.println("jdk se length : " + b.length);
        byteArrayOutputStream.close();
        System.out.println("----------------------------");
        System.out.println("ByteBuffer se length : " + userInfo.codeC().length);
    }
}
