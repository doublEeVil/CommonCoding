package com._22evil.test.aop.entity;

public class Person {
    private int money;      //剩余money
    private boolean isWash; //是否洗手

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isWash() {
        return isWash;
    }

    public void setWash(boolean wash) {
        isWash = wash;
    }
}
