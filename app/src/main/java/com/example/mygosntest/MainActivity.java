package com.example.mygosntest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
                shuzu();
                list();
                userResult();
                userResult2();
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

    //*******泛型*********//
    public void shuzu() {
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        Log.e(" String[]:", strings.toString());
        Log.e(" String[]:", strings[0]);
        Log.e(" String[]:", strings[1]);
    }

    public void list() {  //Gson为我们提供了TypeToken来实现对泛型的支持，所以当我们希望使用将以上的数据解析为List时需要这样写
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
        }.getType());
        Log.e("stringList:", stringList.toString());
        Log.e("stringList:", stringList.size() + "");
    }

    //泛型解析对接口POJO的设计影响
    public void userResult() {
        String json = "{\"code\":\"0\",\"message\":\"success\",\"data\":{\"name\":\"怪盗kidou\",\"email_address\":\"123.com\",\"age\":\"24\"}}";
        Gson gson = new Gson();
        UserResult userResult = gson.fromJson(json, UserResult.class);
        User user = userResult.data;
        Log.e("user", user.name);


        String json2 = "{\"code\":\"0\",\"message\":\"success\",\"data\":[]}";//{"code":"0","message":"success","data":[]}
        //String jsonArray = "{"code":"0","message":"success","data":["name":"怪盗kidou","email_address":"123.com","age":"24"]}";
        //String jsonArray = "{"code":"0","message":"success","data":["怪盗kidou","123.com","24"]}";
        UserListResult userListResult = gson.fromJson(json2, UserListResult.class);
        List<User> users = userListResult.data;
        Log.e("users", users.size() + "");
//        Log.e("users", users.get(0).name);

    }

    //不再重复定义Result类
    public void userResult2() {
        String json = "{\"code\":\"0\",\"message\":\"success\",\"data\":{\"name\":\"怪盗kidou\",\"email_address\":\"123.com\",\"age\":24}}";
        Gson gson = new Gson();
        Type userType = new TypeToken<Result<User>>() {
        }.getType();
        Result<User> userResult = gson.fromJson(json, userType);
        User user = userResult.data;
        Log.e("userResult2", user.name);

        String json2 = "{\"code\":\"0\",\"message\":\"success\",\"data\":[]}";
        Type userListType = new TypeToken<Result<List<User>>>() {
        }.getType();
        Result<List<User>> userListResult = gson.fromJson(json2, userListType);
        List<User> users = userListResult.data;
        Log.e("userResult2", users.size() + "");
    }
}
