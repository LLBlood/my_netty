package cn.liulin.my_netty.SeniorNetty.httpxml;

import java.util.List;

/**
 * cn.liulin.my_netty.SeniorNetty.httpxml$
 *
 * @author ll
 * @date 2023-08-24 11:57:58
 **/
public class Customer {
    private long customerNumber;
    private String firstName;
    private List<String> middleNames;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }
}
