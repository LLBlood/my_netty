package cn.liulin.my_netty.Netty.msgpack;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.Netty.javaSer$
 *
 * @author ll
 * @date 2023-08-15 14:53:38
 **/
@Message
public class UserInfo {
    private String userName;
    private int userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
