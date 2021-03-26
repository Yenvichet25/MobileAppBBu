package com.example.mobileapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ChangeNewPassword extends AppCompatActivity {
    private Sessions sessions;
    private EditText txtNewpass,txtcomfirmpass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new Sessions(this);
        setContentView(R.layout.change_password_layout);

        txtNewpass = (EditText) findViewById(R.id.txtNewPass);
        txtcomfirmpass = (EditText) findViewById(R.id.txtConfirmNewPass);
    }
    public  void UpdateUserPwd(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (txtNewpass.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setMessage("Please enter your new password")
                    .setPositiveButton("ok",null)
                    .show();
            txtNewpass.requestFocus();
        } else if (txtcomfirmpass.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setMessage("Please enter your confirm password")
                    .setPositiveButton("ok",null)
                    .show();
            txtcomfirmpass.requestFocus();
        }else{
            String strNewPwd = SHA1.EncryptPassword(txtNewpass.getText().toString());
            if (strNewPwd.equals(sessions.getPassword())) {
                new AlertDialog.Builder(this)
                        .setMessage("Your password is the same current password.Please enter new password again")
                        .setPositiveButton("ok",null)
                        .show();
            }else{
                if (txtNewpass.getText().toString().equals(txtcomfirmpass.getText().toString())) {
                    String url = getText(R.string.appUrl).toString()+"change_user_password.php";
                    String password = txtNewpass.getText().toString();
                    //change password
                    RequestEditProfile update = new RequestEditProfile(this);
                    update.execute(url,password,""+sessions.getUserId());
                }else{
                    new AlertDialog.Builder(this)
                            .setMessage("Your password does not match your confirm password.Please try again")
                            .setPositiveButton("ok",null)
                            .show();
                }
            }
        }
    }
    public  class RequestEditProfile extends AsyncTask<String,Void,String> {
        private Context context;
        private ProgressDialog dialog;
        private JSONObject object;

        // Constructor
        public  RequestEditProfile (Context context){
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
                //create Url Connection
               URL url = new URL(strings[0]);

                //create HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //configure Url
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                //build post parameters
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("PasswordEdit",strings[1])
                        .appendQueryParameter("UserIdEdit",strings[2]);
                String query = builder.build().getEncodedQuery();

                //open connection
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                //check connection and read to data
                StringBuffer result = new StringBuffer();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    //read data
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = "";
                    while ((line = reader.readLine()) !=null){
                        result.append(line + "\n");
                    }
                }
                return result.toString();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                try {
                    object = new JSONObject(result);
                    if(object.getInt("success")==1){
                        String strNewPwd = SHA1.EncryptPassword(txtNewpass.getText().toString());
                        sessions.setPassword(strNewPwd);
                        Toast.makeText(context,object.getString("msg_success"),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,object.getString("msg_errors"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
