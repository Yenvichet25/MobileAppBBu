package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterUserActivity extends AppCompatActivity {
    EditText txtname, txtpwd;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show layout
        setContentView(R.layout.register_user_layout);
        txtname = (EditText) findViewById(R.id.txtUsernameRegister);
        txtpwd = (EditText) findViewById(R.id.txtPasswordRegister);
    }
    public  void RegisterUser(View view){
        String url = getText(R.string.appUrl).toString() +"register_user.php";
        String name = txtname.getText().toString();
        String pwd = txtpwd.getText().toString();

        // create an object
        RequestRegister reg = new RequestRegister(this);
        reg.execute(url,name,pwd);
    }
    public  class RequestRegister extends AsyncTask<String,Void,String>{
        private Context context;
        private ProgressDialog dialog;
        private JSONObject object;

        // Constructor
        public  RequestRegister (Context context){
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
                pairs.add(new BasicNameValuePair("UsernameRes",strings[1]));
                pairs.add(new BasicNameValuePair("PasswordRes",strings[2]));

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
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                try {
                    object = new JSONObject(s);
                    if(object.getInt("success")==1){
                        Toast.makeText(context,object.getString("msg_success"),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,object.getString("msg_errors"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }

    }
}
