package com.example.mobileapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.ContactActivity;
import com.example.mobileapp.ContactDetail;
import com.example.mobileapp.R;
import com.example.mobileapp.adapter.ContactAdapter;
import com.example.mobileapp.models.ContactItems;
import com.example.mobileapp.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class Contact extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        List<ContactItems> listContact = new ArrayList<ContactItems>();
        View v = inflater.inflate(R.layout.tab_contact_layout,container,false);

        listContact.add(new ContactItems(R.drawable.group,"ABC Beer","09890880","kakaka@gmail.com"));
        listContact.add(new ContactItems(R.drawable.group,"ABC Beer","09890880","kakaka@gmail.com"));
        listContact.add(new ContactItems(R.drawable.group,"ABC Beer","09890880","kakaka@gmail.com"));
        listContact.add(new ContactItems(R.drawable.group,"ABC Beer","09890880","kakaka@gmail.com"));

        ContactAdapter adapter = new ContactAdapter(getContext(),listContact);
       ListView LV = (ListView) v.findViewById(R.id.LvTabContact);
        LV.setAdapter(adapter);

        LV.setOnItemClickListener(this);
        LV.setOnItemLongClickListener(this);
        return v;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        viewItems(view);
    }

    private void viewItems(View view){
        //Toast.makeText(this,"This is short Click",Toast.LENGTH_LONG).show();
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.tvName);
        TextView phone = (TextView) view.findViewById(R.id.tvNumber);
        TextView email = (TextView) view.findViewById(R.id.tvEmail);
        Intent in = new Intent(getContext(), ContactDetail.class);
        in.putExtra("IMAGE_ID",img.getTag().toString());
        in.putExtra("CONTACT_NAME",name.getText().toString());
        in.putExtra("CONTACT_PHONE",phone.getText().toString());
        in.putExtra("CONTACT_EMAIL",email.getText().toString());
        startActivity(in);
    };

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final  View view, int position, long id) {
        final String[] action = {"Add New","Edit","Delete","View","Add To Favorites"};
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
//                Toast.makeText(ContactActivity.this,action[i],Toast.LENGTH_LONG).show();
                if(action[i].equals("View")){
                    viewItems(view);
                }
                viewItems(view);
            }
        });
        ad.show();
        return true;
    }
}
