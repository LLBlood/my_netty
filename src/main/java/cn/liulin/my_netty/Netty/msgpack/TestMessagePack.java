package cn.liulin.my_netty.Netty.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * cn.liulin.my_netty.Netty.msgpack$
 *
 * @author ll
 * @date 2023-08-15 15:43:35
 **/
public class TestMessagePack {
    public static void main(String[] args) throws IOException {
//        List<String> list = new ArrayList<>();
//        list.add("sdfasfas");
//        list.add("gsdfafas");
//        list.add("fdgf");
        MessagePack messagePack = new MessagePack();
//        byte[] write = messagePack.write(list);
//        List<String> read = messagePack.read(write, Templates.tList(Templates.TString));
//        System.out.println(read);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("ABCD==------->" + 1);
        userInfo.setUserId(1);
        byte[] write = messagePack.write(userInfo);
        System.out.println(write);
    }
}
