package com.wuyi.redis;

/**
 * @Author: WuYi at 2023-04-13 20:38
 * @Description:
 * @Version:1.0
 */
public abstract class Customer {
    protected Double amount;

    abstract boolean openAccount();

    abstract Double save(Double cash);

    abstract Double withdraw(Double cash);

    abstract void check();
}

class creditCustomer extends Customer {
    private int maxBorrow = -10000;

    @Override
    boolean openAccount() {
        return false;
    }

    @Override
    Double save(Double cash) {
        this.amount += cash;
        return amount;
    }

    @Override
    Double withdraw(Double cash) {
        this.amount -= cash;
        return amount;
    }

    @Override
    void check() {
        System.out.println(amount);
    }
}

class cashCustomer extends Customer {

    private int maxBorrow = 0;
    private int maxCustomerCount;
    private int dailyCount;
    private int yearlyCount;

    @Override
    boolean openAccount() {
        return false;
    }

    @Override
    Double save(Double cash) {
        return 0;
    }

    @Override
    Double withdraw(Double cash) {
        return 0;
    }

    @Override
    void check() {

    }
}


class cashCustomer1 extends cashCustomer {
    private int maxCustomerCount = 1;

}

class cashCustomer2 extends cashCustomer {
    private int maxCustomerCount = 5;
    private int dailyCount = 10000;
    private int yearlyCount = 20 * 10000;
}


