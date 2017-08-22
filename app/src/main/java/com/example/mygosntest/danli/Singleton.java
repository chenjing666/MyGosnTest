package com.example.mygosntest.danli;

/**
 * 懒汉式线程安全单例
 * Created by Administrator on 2017/8/22 0022.
 */

public class Singleton {
    private volatile static Singleton INSTANCE; //声明成 volatile

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

}
