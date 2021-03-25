package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckCurrentPassword extends AppCompatActivity {
    private Sessions sessions;
    private EditText txtcurrentpass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_current_password);
        sessions = new Sessions(this);
        txtcurrentpass = (EditText) findViewById(R.id.txtcheckcurrentpass);
    }
    public  void CheckCurrentPWD(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(txtcurrentpass.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setMessage("Please enter your current password!")
                    .setPositiveButton("Ok",null)
                    .show();
            txtcurrentpass.requestFocus();
        }else{
            String PWD = EncryptPassword(txtcurrentpass.getText().toString());
            if(sessions.getPassword().equals(PWD)){
                Intent in = new Intent(CheckCurrentPassword.this,ChangeNewPassword.class);
                startActivity(in);
                finish();
            }else{
                new AlertDialog.Builder(this)
                        .setMessage("Your current password is wrong!")
                        .setPositiveButton("Ok",null)
                        .show();
            }
        }
    }
    public static String EncryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(password.getBytes("UTF-8"));
        return  new BigInteger(1,md.digest()).toString(16);
    }
}
