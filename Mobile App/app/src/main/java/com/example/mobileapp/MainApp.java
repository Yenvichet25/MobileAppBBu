package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainApp extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }
    public void ViewContact(View v){
        Intent in =new Intent(MainApp.this,ContactActivity.class);
        startActivity(in);
    }
    public void ViewProduct(View v){
        Intent in =new Intent(MainApp.this,ProductActivity.class);
        startActivity(in);
    }
}
