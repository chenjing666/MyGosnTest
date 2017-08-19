package com.example.mygosntest.jinjie;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class Category {//在使用Gson时也不能只是简单的new Gson()了。
//    使用方法： 简单说来就是需要导出的字段上加上@Expose 注解，不导出的字段不加

//    @Expose //
//    @Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效
//    @Expose(deserialize = true,serialize = false) //反序列化时生效
//    @Expose(deserialize = false,serialize = true) //序列化时生效
//    @Expose(deserialize = false,serialize = false) // 和不写一样

//    {
//        "id": 1,
//            "name": "电脑",
//            "children": [
//        {
//            "id": 100,
//                "name": "笔记本"
//        },
//        {
//            "id": 101,
//                "name": "台式机"
//        }
//        ]
//    }

    @Expose
    public int id;
    @Expose
    public String name;
    @Expose
    public List<Category> children;

    //因业务需要增加，但并不需要序列化
    public Category parent; //不需要序列化,所以不加 @Expose 注解,等价于 @Expose(deserialize = false,serialize = false)

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id, String name, List<Category> children, Category parent) {
        this.id = id;
        this.name = name;
        this.children = children;
        this.parent = parent;
    }
}
