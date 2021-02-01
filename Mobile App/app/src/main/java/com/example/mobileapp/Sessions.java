package com.example.mobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessions {
    // data member
    private SharedPreferences sp;
    //constructor
    public Sessions(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }
    //setter methods
    public void setUserId(int UserId){
        sp.edit().putInt("USERID",UserId).commit();
    }
    public void  setUsername(String Username){
        sp.edit().putString("USERNAME",Username).commit();
    }
    public  void  setPassword(String Password){
        sp.edit().putString("USERPASSWORD",Password).commit();
    }
    public  void  setUserEmail(String UserEmail){
        sp.edit().putString("USEREMAIL",UserEmail).commit();
    }
    public  void  setUserImg(String UserImg){
        sp.edit().putString("USERIMAGE",UserImg).commit();
    }
    //getter method
    public int getUserId(int userId){
        int id = sp.getInt("USERID",0);
        return id;
    }
    public String getUsername(String username){
        return sp.getString("USERNAME","");
    }
    public String getPassword(String password){
        return sp.getString("USERPASSWORD","");
    }
    public String getUserEmail(String userEmail){
        return sp.getString("USEREMAIL","");
    }
    public String getUserImg(String userImg){
        return sp.getString("USERIMAGE","");
    }
    public  void ClearSessions(){
        sp.edit().clear().commit();
    }
}
