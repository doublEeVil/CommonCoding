package com._22evil.effective_example.e004;

/**
 * 利用枚举实现单例模式
 */
public enum Singleton {
    INSTANCE;

    public void print() {

    }

    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        instance.print();
    }
}