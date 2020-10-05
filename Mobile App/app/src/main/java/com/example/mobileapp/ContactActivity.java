package com.example.mobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactItems> lstContact = new ArrayList<ContactItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show layout
        setContentView(R.layout.contact_layout);

        lstContact.add(new ContactItems(R.drawable.contact,"Dara","01267687","dara@gmail.com"));
        lstContact.add(new ContactItems(R.drawable.group,"Sok kha","012123445","sokkha009@gmail.com"));

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
