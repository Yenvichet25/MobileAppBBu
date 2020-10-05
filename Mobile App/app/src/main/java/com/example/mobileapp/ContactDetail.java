package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetail extends AppCompatActivity {
    ImageView img;
    TextView name, phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        img = (ImageView) findViewById(R.id.imageView2);
        img.setImageResource(Integer.parseInt(getIntent().getStringExtra("IMAGE_ID")));
        name = (TextView) findViewById(R.id.tvshowname);
        name.setText(getIntent().getStringExtra("CONTACT_NAME"));
        phone = (TextView) findViewById(R.id.tvshowphone);
        phone.setText(getIntent().getStringExtra("CONTACT_PHONE"));
        email = (TextView) findViewById(R.id.tvshowemail);
        email.setText(getIntent().getStringExtra("CONTACT_EMAIL"));

        ActionBar br = getSupportActionBar();
        br.setTitle(getIntent().getStringExtra("CONTACT_NAME"));
    }
}
