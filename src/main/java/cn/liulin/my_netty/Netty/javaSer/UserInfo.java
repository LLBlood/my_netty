package cn.liulin.my_netty.Netty.javaSer;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * cn.liulin.my_netty.Netty.javaSer$
 *
 * @author ll
 * @date 2023-08-15 14:53:38
 **/
public class UserInfo implements Serializable {
    private String userName;
    private int userId;
    private static final long serialVersionUID = 1L;

    public UserInfo buildUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final int getUserId() {
        return userId;
    }

    public final void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] codeC() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = this.userName.getBytes(StandardCharsets.UTF_8);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.putInt(this.userId);
        byteBuffer.flip();
        bytes = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }

    public byte[] codeC(ByteBuffer byteBuffer) {
        byteBuffer.clear();
        byte[] bytes = this.userName.getBytes(StandardCharsets.UTF_8);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.putInt(this.userId);
        byteBuffer.flip();
        bytes = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }
}
