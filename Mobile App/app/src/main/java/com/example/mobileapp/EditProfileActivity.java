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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.mobileapp.R.id.edit_profile;
import static com.example.mobileapp.R.id.fill;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView editImage;
    private EditText editName,editEmail;
    private Sessions sessions;
    private  Bitmap bitmap;
    private final  int GALLERY = 1;
    private  final  int CAMERA = 2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new Sessions(this);
        setContentView(R.layout.edit_profile);

        editName = (EditText) findViewById(R.id.txteditproname);
        editName.setText(sessions.getUsername());

        editEmail = (EditText) findViewById(R.id.txteditproemail);
        editEmail.setText(sessions.getUserEmail());

        bitmap = StringToImage(sessions.getUserImg());
        editImage = (ImageView) findViewById(R.id.imgEditProPhoto);
        editImage.setImageBitmap(bitmap);
        editImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,210,false));

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] action = {"From Gallery","Take Your Photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(EditProfileActivity.this);
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
                });
                ad.show();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                editImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CAMERA && resultCode == RESULT_OK && data != null){
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                editImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Bitmap StringToImage(String userImg) {
        Bitmap bitmap = null;
        byte[] image = Base64.decode(userImg,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return  bitmap;
    }

    public  class RequestEditProfile extends AsyncTask<String,Void,String>{
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
                //create HttpClient
                HttpClient client = new DefaultHttpClient();
                //create HttpPost
                HttpPost post = new HttpPost(strings[0]);
                //build post parameters
                List<NameValuePair> pairs= new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("UserIdEdit",strings[1]));
                pairs.add(new BasicNameValuePair("UserNameEdit",strings[2]));
                pairs.add(new BasicNameValuePair("UserEmailEdit",strings[3]));
                pairs.add(new BasicNameValuePair("UserImageEdit",strings[4]));

                //URL ending code
                post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));

                //final making Http request
                HttpResponse response = client.execute(post);

                //write errors  to log
                Log.e("HttpResponse",response.toString());

                //read data send from server
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
        protected void onPostExecute(String result) {
            if(result != null){
                try {
                    object = new JSONObject(result);
                    if(object.getInt("success")==1){
                       sessions.setUsername(editName.getText().toString());
                       sessions.setUserEmail(editEmail.getText().toString());
                       sessions.setUserImg(object.getString("UserImageUpdate"));
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
    public void EditUserProfile(View view){
        String url  = getText(R.string.appUrl).toString()+"edit_profile.php";
        String id = ""+sessions.getUserId();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String img = ImageToString(bitmap);
        RequestEditProfile editProfile = new RequestEditProfile(this);
        editProfile.execute(url,id,name,email,img);
    }

    private String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArr);
        byte[] Bytes = byteArr.toByteArray();
        return Base64.encodeToString(Bytes,Base64.DEFAULT);
    }
}
