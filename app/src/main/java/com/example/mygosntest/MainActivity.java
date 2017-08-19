package com.example.mygosntest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mygosntest.jinjie.NewActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
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
    @BindView(R.id.unXL)
    Button unXL;
    @BindView(R.id.isXL)
    Button isXL;
    @BindView(R.id.gsonbuilder)
    Button gsonbuilder;
    @BindView(R.id.jinjie)
    Button jinjie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    //Gson提供了fromJson()和toJson() 两个直接用于解析和生成的方法，前者实现反序列化，后者实现了序列化。
    //同时每个方法都提供了重载方法，我常用的总共有5个。
    //Gson.toJson(Object);
    //Gson.fromJson(Reader,Class);
    //Gson.fromJson(String,Class);
    //Gson.fromJson(Reader,Type);
    //Gson.fromJson(String,Type);

    @OnClick({R.id.jibenyongfa, R.id.fanxing, R.id.unXL, R.id.isXL, R.id.gsonbuilder, R.id.jinjie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jinjie:
                startActivity(new Intent(MainActivity.this, NewActivity.class));
                break;
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
            case R.id.unXL:
                try {
                    myunXL();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.isXL:
                try {
                    myXL();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.gsonbuilder:
                //使用GsonBuilder导出null值、格式化输出、日期时间
                myGsonBuilder();
                break;
        }
    }

    private void myGsonBuilder() {
        //Gson在默认情况下是不动导出值null的键的
        Gson gson1 = new Gson();
        User user1 = new User("怪盗kidou", 24);
        Log.e("user1", gson1.toJson(user1));
        System.out.println(gson1.toJson(user1)); //{"name":"怪盗kidou","age":24}

        //这样就有了
        Gson gson = new GsonBuilder()
                //各种配置
                .serializeNulls()
                .create(); //生成配置好的Gson
        User user = new User("怪盗kidou", 24);
        Log.e("user", gson.toJson(user));
        System.out.println(gson.toJson(user)); //{"name":"怪盗kidou","age":24,"email":null}

        //其他用法
        Gson gson2 = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
    }

    /**
     * Gson的流式反序列化
     */
    public void myunXL() throws IOException {
        String json = "{\"name\":\"怪盗kidou\",\"age\":\"24\"}";
        User user = new User();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.beginObject(); // throws IOException
        while (reader.hasNext()) {
            String s = reader.nextName();
            switch (s) {
                case "name":
                    user.name = reader.nextString();
                    break;
                case "age":
                    user.age = reader.nextInt(); //自动转换
                    break;
//                case "email":
//                    user.email = reader.nextString();
//                    break;
            }
        }
        reader.endObject(); // throws IOException
        System.out.println(user.name);  // 怪盗kidou
        System.out.println(user.age);   // 24
//        System.out.println(user.email); // ikidou@example.com
    }

    /**
     * Gson的流式序列化
     */
    public void myXL() throws IOException {
        //自动
        Gson gson = new Gson();
        User user = new User("怪盗kidou", 24, "ikidou@example.com");
        gson.toJson(user, System.out); // 写到控制台

        //手动
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
        writer.beginObject() // throws IOException
                .name("name").value("怪盗kidou")
                .name("age").value(24)
                .name("email_address").nullValue() //演示null
                .endObject(); // throws IOException
        writer.flush(); // throws IOException
        //{"name":"怪盗kidou","age":24,"email":null}
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
