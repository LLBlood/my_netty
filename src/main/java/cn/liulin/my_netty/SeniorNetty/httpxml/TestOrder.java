package cn.liulin.my_netty.SeniorNetty.httpxml;

import com.alibaba.fastjson.JSON;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml$
 *
 * @author ll
 * @date 2023-08-28 17:39:32
 **/
public class TestOrder {
    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF-8";
    private String encode2Xml(Order order) throws Exception {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws Exception {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        return (Order) uctx.unmarshalDocument(reader);
    }

    public static void main(String[] args) throws Exception {
        TestOrder testOrder = new TestOrder();
        Order order = OrderFactory.create(123);
        String xml = testOrder.encode2Xml(order);
        Order order1 = testOrder.decode2Order(xml);
        System.out.println(JSON.toJSONString(order1));

    }
}
