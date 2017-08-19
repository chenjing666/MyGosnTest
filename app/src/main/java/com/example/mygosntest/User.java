package com.example.mygosntest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class User {
    public String name;
    public int age;
    //@SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
    // 当上面的三个属性(email_address、email、emailAddress)都中出现任意一个时均可以得到正确的结果
    // 当多种情况同时出时，以最后一个出现的值为准
    @SerializedName("email_address")//对于json中email_address这个属性对应POJO的属性则变成
    public String emailAddress;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    public User() {
    }
}
