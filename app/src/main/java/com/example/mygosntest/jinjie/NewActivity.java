package com.example.mygosntest.jinjie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.mygosntest.R;
import com.example.mygosntest.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewActivity extends AppCompatActivity {

    @BindView(R.id.TypeAdapter)
    Button TypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
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

        Category category = new Category(2, "hh", null, null);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String string = gson.toJson(category);
        Log.e("string", string);
    }

    @OnClick(R.id.TypeAdapter)
    public void onViewClicked() {

//        当我们为User.class 注册了 TypeAdapter之后，只要是操作User.class 那些之前介绍的@SerializedName 、
//        FieldNamingStrategy、Since、Until、Expos通通都黯然失色，失去了效果，只会调用我们实现的
//        UserTypeAdapter.write(JsonWriter, User) 方法，我想怎么写就怎么写
        User user = new User("怪盗kidou", 24);
        user.emailAddress = "ikidou@example.com";
        Gson gson = new GsonBuilder()
                //为User注册TypeAdapter
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
        System.out.println(gson.toJson(user));
        Log.e("string", gson.toJson(user));
    }

}
