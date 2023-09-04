package cn.liulin.my_netty.SeniorNetty.self;

/**
 * cn.liulin.my_netty.SeniorNetty.self$
 *
 * @author ll
 * @date 2023-09-04 09:43:31
 **/
public class NettyMessage {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
