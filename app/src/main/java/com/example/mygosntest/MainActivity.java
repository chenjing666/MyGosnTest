package com.example.mygosntest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.jibenyongfa)
    Button jibenyongfa;
    @BindView(R.id.fanxing)
    Button fanxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.jibenyongfa, R.id.fanxing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jibenyongfa:
                jiexiGson();//基本数据类型解析
                shengchengGson();//基本数据类型生成
                pojoToJson();//生成JSON
                jiexiJson();//解析JSON
                break;
            case R.id.fanxing:
                break;
        }
    }

    public void jiexiGson() {
        Gson gson = new Gson();
        int i = gson.fromJson("100", int.class);              //100
        double d = gson.fromJson("\"99.99\"", double.class);  //99.99
        boolean b = gson.fromJson("true", boolean.class);     // true
        String str = gson.fromJson("String", String.class);   // String
        Log.e("i:", i + "");
        Log.e("d:", d + "");
        Log.e("b:", b + "");
        Log.e("str:", str);
    }

    public void shengchengGson() {
        Gson gson = new Gson();
        String jsonNumber = gson.toJson(100.2);       // 100
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"
        Log.e("jsonNumber:", jsonNumber);
        Log.e("jsonBoolean:", jsonBoolean);
        Log.e("jsonString:", jsonString);
    }

    public void pojoToJson() {
        Gson gson = new Gson();
        User user = new User("怪盗kidou", 24, "123.com");
        String jsonObject = gson.toJson(user); // {"name":"怪盗kidou","age":24}
        Log.e("jsonObject:", jsonObject);
    }

    public void jiexiJson() {
        Gson gson = new Gson();
        String jsonString = "{\"name\":\"怪盗kidou\",\"email_address\":\"123.com\",\"age\":24}";
        User user = gson.fromJson(jsonString, User.class);
        Log.e("user:", user.name);
        Log.e("user:", user.emailAddress);
        Log.e("user:", user.age + "");
    }


}
