package com.example.mobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainApp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Sessions sessions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new Sessions(this);
        setContentView(R.layout.navigation_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.navId);
        nav .setNavigationItemSelectedListener(this);

        View v = nav.getHeaderView(0);
        TextView logname = (TextView) v.findViewById(R.id.tvHeaderLogName);
        logname.setText(sessions.getUsername());

        TextView logemail = (TextView) v.findViewById(R.id.tvHeaderLogEmail);
        logemail.setText(sessions.getUserEmail());

        ImageView img = (ImageView) v.findViewById(R.id.imgHeader);
        Bitmap bitmap = StringToImage(sessions.getUserImg());
        img.setImageBitmap(Bitmap.createScaledBitmap(bitmap,190,200,false));

        ImageButton btn = (ImageButton) v .findViewById(R.id.btneditImgPro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainApp.this,EditProfileActivity.class);
                startActivity(in);
            }
        });
    }

    private Bitmap StringToImage(String userImg) {
        Bitmap bitmap = null;
        byte[] image = Base64.decode(userImg,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return  bitmap;
    }

    public void ViewContact(View v){
        Intent in =new Intent(MainApp.this,ContactActivity.class);
        startActivity(in);
    }
    public void ViewProduct(View v){
        Intent in =new Intent(MainApp.this,ProductActivity.class);
        startActivity(in);
    }
    public void ViewGroup(View v){
        Intent in =new Intent(MainApp.this,MainGroupLayout.class);
        startActivity(in);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.mnProduct){
            Intent in =new Intent(MainApp.this,ProductActivity.class);
            startActivity(in);
        }else if(id == R.id.mnContact){
            Intent in =new Intent(MainApp.this,ContactActivity.class);
            startActivity(in);
        }else if(id == R.id.mnHome){
            Intent in =new Intent(MainApp.this,MainGroupLayout.class);
            startActivity(in);
        }else if(id == R.id.menuLogout){
            sessions.ClearSessions();
            Intent in =new Intent(MainApp.this,MainActivity.class);
            startActivity(in);
            finish();
        }else if(id == R.id.menuchangepwd){
            Intent in =new Intent(MainApp.this,CheckCurrentPassword.class);
            startActivity(in);
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
