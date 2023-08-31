package cn.liulin.my_netty.SeniorNetty.httpxml;

import java.util.Arrays;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml$
 *
 * @author ll
 * @date 2023-08-28 17:55:12
 **/
public class OrderFactory {
    public static Order create(int code) {
        Order order = new Order();
        order.setOrderNumber(code);
        order.setShipping(Shipping.DOMESTIC_EXPRESS);
        order.setTotal(100.25f);
        Address address = new Address();
        address.setPostCode("121212");
        address.setCity("山东");
        address.setCountry("中国");
        address.setStreet1("撒地方");
        address.setState("121");
        address.setStreet2("撒地方2");
        order.setBillTo(address);
        order.setShipTo(address);
        Customer customer = new Customer();
        customer.setCustomerNumber(51555);
        customer.setFirstName("张");
//        customer.setMiddleNames(Arrays.asList("12121", "s", "瑟夫"));
        order.setCustomer(customer);
        return order;
    }
}
