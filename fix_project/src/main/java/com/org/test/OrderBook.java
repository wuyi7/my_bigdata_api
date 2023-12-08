package com.org.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WuYi at 2023-05-11 14:24
 * @Description:
 * @Version:1.0
 */
public class OrderBook {

    private final List<FixMsg> orderPools = new ArrayList<>();

    public boolean sendOrder(FixMsg fixMsg) {
        for (FixMsg pendingFixMsg : orderPools) {
            if (fixMsg.isMatch(pendingFixMsg)) {
                fixMsg.setState("fill");
                return true;
            }
        }
        //未匹配订单进入缓存
        orderPools.add(fixMsg);
        return false;
    }

    public boolean cancelOrder(FixMsg fixMsg) {
        return orderPools.removeIf(fixMsg::isMatch);
    }

    public boolean changeOrder(FixMsg fixMsg) {
        for (FixMsg pendingFixMsg : orderPools) {
            if (fixMsg.isMatch(pendingFixMsg)) {
                pendingFixMsg.setCount(fixMsg.getCount());
                return true;
            }
        }
        //未匹配订单则修改失败
        return false;
    }

    public void showOrderList() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Order " + (i + 1) + ":" + orderPools.get(i));
        }
    }


    public static void main(String[] args) {
        FixMsg fixMsg1 = new FixMsg("");
        FixMsg fixMsg2 = new FixMsg("");
        FixMsg fixMsg3 = new FixMsg("");
        FixMsg fixMsg4 = new FixMsg("");
        FixMsg fixMsg5 = new FixMsg("");



    }
}

class FixMsg {
    private String state;
    private int count;
    private String fixMsgDetails;

    public FixMsg(String fixMsgString) {
        String[] split = fixMsgString.split(";");
        this.state = split[2];
        this.count = Integer.parseInt(split[3]);
        this.fixMsgDetails = split[4];
    }

    public boolean isMatch(FixMsg pendingFixMsg) {
        //交易细节相同则为同一订单,则成交
        return this.getFixMsgDetails().equals(pendingFixMsg.getFixMsgDetails());
    }

    @Override
    public String toString() {
        return "FixMsg{" +
                "state='" + state + '\'' +
                ", count=" + count +
                ", fixMsgDetails='" + fixMsgDetails + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFixMsgDetails() {
        return fixMsgDetails;
    }

    public void setFixMsgDetails(String fixMsgDetails) {
        this.fixMsgDetails = fixMsgDetails;
    }


}
