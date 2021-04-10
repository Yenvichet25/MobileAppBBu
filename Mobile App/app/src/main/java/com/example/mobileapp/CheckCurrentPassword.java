package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.function.SHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CheckCurrentPassword extends AppCompatActivity {
    private Sessions sessions;
    private EditText txtcurrentPWD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_current_password);
        sessions = new Sessions(this);
        txtcurrentPWD = (EditText) findViewById(R.id.txtcheckcurrentpass);
    }
    public  void CheckCurrentPWD(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(txtcurrentPWD.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setMessage("Please enter your current password!")
                    .setPositiveButton("Ok",null)
                    .show();
            txtcurrentPWD.requestFocus();
        }else{
            String PWD = SHA1.EncryptPassword(txtcurrentPWD.getText().toString());
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

}
