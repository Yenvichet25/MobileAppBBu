package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.function.ConvertImages;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddNewContacts extends AppCompatActivity {
    private Sessions sessions;
    private EditText txtContactName,txtContactNumber,txtContactEmail;
    private Bitmap bitmap;
    private ImageView imgContactPhoto;
    private Button btnSave,btnCancel;
    private final  int GALLERY = 1;
    private  final  int CAMERA = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new Sessions(this);
        setContentView(R.layout.add_new_contact_layout);

        txtContactName = (EditText) findViewById(R.id.txtNewContactName);
        txtContactNumber = (EditText) findViewById(R.id.txtNewContactNum);
        txtContactEmail = (EditText) findViewById(R.id.txtNewContactEmail);
        bitmap = ConvertImages.StringToImage(sessions.getUserImg());
        imgContactPhoto = (ImageView) findViewById(R.id.imgNewContact);
        imgContactPhoto.setImageBitmap(bitmap);
        imgContactPhoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,210,false));
        btnSave = (Button) findViewById(R.id.btnSaveNewContact);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtContactName.getText().toString().equals("")){
                    new android.app.AlertDialog.Builder(AddNewContacts.this)
                            .setMessage("Please enter your contact name")
                            .setPositiveButton("ok",null)
                            .show();
                }else if(txtContactNumber.getText().toString().equals("")){
                    new android.app.AlertDialog.Builder(AddNewContacts.this)
                            .setMessage("Please enter your contact number")
                            .setPositiveButton("ok",null)
                            .show();
                }else if(txtContactEmail.getText().toString().equals("")){
                    new android.app.AlertDialog.Builder(AddNewContacts.this)
                            .setMessage("Please enter your contact email")
                            .setPositiveButton("ok",null)
                            .show();
                }else{
                    String url = getText(R.string.appUrl).toString()+"add_new_contact.php";
                    String conName = txtContactName.getText().toString();
                    String conPhone = txtContactNumber.getText().toString();
                    String conEmail = txtContactEmail.getText().toString();
                    String conImage = ConvertImages.ImageToString(bitmap);
                    RequestAddContact add = new RequestAddContact(AddNewContacts.this);
                    add.execute(url,conName,conPhone,conEmail,conImage);
                }
            }
        });

        imgContactPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] action = {"From Gallery","Take Your Photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(AddNewContacts.this);
                ad.setTitle("Please Choose:");
                ad.setIcon(R.drawable.ic_baseline_photo_24);
                ad.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(action[i].equals("From Gallery")){
                            ShowGallery();
                        }else if(action[i].equals("Take Your Photo")){
                            TakePhoto();
                        }
                    }

                    private void TakePhoto() {
                        Intent tp = new Intent();
                        tp.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(tp,CAMERA);
                    }

                    private void ShowGallery() {
                        Intent gal = new Intent();
                        gal.setType("image/*");
                        gal.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(gal,GALLERY);
                    }
                });
                ad.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imgContactPhoto.setImageBitmap(bitmap);
                imgContactPhoto.setTag("true");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CAMERA && resultCode == RESULT_OK && data != null){
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                imgContactPhoto.setImageBitmap(bitmap);
                imgContactPhoto.setTag("true");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class RequestAddContact extends AsyncTask<String,Void,String>{
        private Context context;
        private ProgressDialog dialog;
        private JSONObject object;
        // Constructor
        public  RequestAddContact (Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Waiting...");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                //createUrl
                URL url = new URL(strings[0]);
                //httpUrlConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //configureURl
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                //build post parameters
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("ContactName",strings[1])
                        .appendQueryParameter("ContactNumber",strings[2])
                        .appendQueryParameter("ContactEmail",strings[3])
                        .appendQueryParameter("ContactImage",strings[4])
                        ;
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
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                try {
                    object = new JSONObject(result);
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
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
