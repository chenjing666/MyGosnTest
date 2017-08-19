package com.example.mygosntest.jinjie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mygosntest.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
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
}
