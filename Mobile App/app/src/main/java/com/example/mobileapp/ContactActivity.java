package com.example.mobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.adapter.ContactAdapter;
import com.example.mobileapp.models.ContactItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactItems> lstContact = new ArrayList<ContactItems>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show layout
        setContentView(R.layout.contact_layout);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);
        try {
            String strUrl = getText(R.string.appUrl).toString() + "show_contact.php";
            String imgPath = getText(R.string.appUrl).toString()+ "contacts/";
            //createUrl
            URL url = new URL(strUrl);
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
                    .appendQueryParameter("ContactTag","showcontact");
            String query = builder.build().getEncodedQuery();
            //open connection
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            //check connection and read to data
            StringBuffer buffer = new StringBuffer();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                //read data
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line = "";
                while ((line = reader.readLine()) !=null){
                    buffer.append(line + "\n");
                }
                JSONObject obj = new JSONObject(buffer.toString());
                JSONArray arr = obj.getJSONArray("contacts");
                for(int i = 0; i<arr.length(); i++){
                    JSONObject object = arr.getJSONObject(i);
                    lstContact.add(new ContactItems(
                            imgPath + object.getString("ContactImage"),
                            object.getString("ContactName"),
                            object.getString("ContactNumber"),
                            object.getString("ContactEmail")));
                }
            }else{
                new AlertDialog.Builder(ContactActivity.this)
                        .setMessage("Failed to connection.")
                        .setPositiveButton("Ok",null)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ContactAdapter adapter = new ContactAdapter(this,lstContact);
        ListView LV = (ListView) findViewById(R.id.Lvcontact);
        LV.setAdapter(adapter);
        LV.setOnItemClickListener(this);
        LV.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewItems(view);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

        final String[] action = {"Add New","Edit","Delete","View","Add To Favorites"};
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
         ad.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(ContactActivity.this,action[i],Toast.LENGTH_LONG).show();
                viewItems(view);
            }
        });

        ad.show();
        return true;
    }

    private void viewItems(View view){
        //Toast.makeText(this,"This is short Click",Toast.LENGTH_LONG).show();
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.tvName);
        TextView phone = (TextView) view.findViewById(R.id.tvNumber);
        TextView email = (TextView) view.findViewById(R.id.tvEmail);
        Intent in = new Intent(ContactActivity.this,ContactDetail.class);
        in.putExtra("IMAGE_ID",img.getTag().toString());
        in.putExtra("CONTACT_NAME",name.getText().toString());
        in.putExtra("CONTACT_PHONE",phone.getText().toString());
        in.putExtra("CONTACT_EMAIL",email.getText().toString());
        startActivity(in);
    };
}
