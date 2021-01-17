package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }
    public void Login(View v){
        EditText name = (EditText) findViewById(R.id.txtname);
        EditText pwd = (EditText) findViewById(R.id.pwd);
        if(name.getText().toString().equals("")){
            Toast.makeText(this,"User Name is required!", Toast.LENGTH_LONG).show();
            name.requestFocus();
        }else if (pwd.getText().toString().equals("")){
            Toast.makeText(this,"Password is required!",Toast.LENGTH_LONG).show();
            pwd.requestFocus();
        }else {
            if(name.getText().toString().equals("admin") && pwd.getText().toString().equals("admin")){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                //setContentView(R.layout.main_menu);
                Intent in = new Intent(MainActivity.this,MainApp.class);
                startActivity(in);
            }else {
                Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
    }
    public  void GoToSignUp(View view){
        Intent signup = new Intent(MainActivity.this,RegisterUserActivity.class);
        startActivity(signup);
    }
}