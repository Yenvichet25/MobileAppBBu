package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        sessions = new Sessions(this);
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
            String url = getText(R.string.appUrl).toString() +"login_user.php";
            String username = name.getText().toString();
            String userpass = pwd.getText().toString();

            // create an object
            RequestLogin login = new RequestLogin(this);
            login.execute(url,username,userpass);
        }
    }
    public  void GoToSignUp(View view){
        Intent signup = new Intent(MainActivity.this,RegisterUserActivity.class);
        startActivity(signup);
    }
    public  class RequestLogin extends AsyncTask<String,Void,String>{
        private Context context;
        private ProgressDialog dialog;
        private JSONObject object;

        // Constructor
        public  RequestLogin (Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //create HttpClient
                HttpClient client = new DefaultHttpClient();

                //create HttpPost
                HttpPost post = new HttpPost(strings[0]);

                //build post parameters
                List<NameValuePair> pairs= new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("UsernameLogin",strings[1]));
                pairs.add(new BasicNameValuePair("PasswordLogin",strings[2]));

                //URL ending code
                post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));

                //final making Http request
                HttpResponse response = client.execute(post);

                //write errors  to log
                Log.d("HttpResponse",response.toString());
                InputStream is = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) !=null){
                    result.append(line + "\n");
                }
                return  result.toString();
            }catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           if(result != null){
               try {
                   JSONObject object = new JSONObject(result);
                   if(object.getInt("success")==1){
                       sessions.getUserId(object.getInt("UserId"));
                       sessions.getUsername(object.getString("Username"));
                       sessions.getPassword(object.getString("Password"));
                       sessions.getUserEmail(object.getString("UserEmail"));
                       sessions.getUserImg(object.getString("UserImg"));
                       //setContentView(R.layout.main_menu);
                       Intent in = new Intent(MainActivity.this,MainApp.class);
                       startActivity(in);
                       finish();
                   }else{
                       new AlertDialog.Builder(context)
                               .setTitle("Login Failed")
                               .setIcon(R.drawable.ic_baseline_error_24)
                               .setMessage(object.getString("msg_errors"))
                               .setPositiveButton("OK",null)
                               .show();
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
        }
    }
}