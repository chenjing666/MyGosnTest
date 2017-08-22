package com.example.mygosntest.danli;

/**
 * 内部类实现单例
 * Created by Administrator on 2017/8/22 0022.
 */

public class Singleton2 {
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static final Singleton2 instance = new Singleton2();
    }

    /**
     * 私有化构造方法
     */
    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return SingletonHolder.instance;
    }
}
