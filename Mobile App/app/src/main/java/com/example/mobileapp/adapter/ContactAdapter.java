package com.example.mobileapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.ContactItems;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    //data member
    private Context context;
    private List<ContactItems> lstcontact;
    //contractor
    public ContactAdapter(Context context,List<ContactItems> lst){
        this.context=context;
        this.lstcontact=lst;
    }
    @Override
    public int getCount() {
        return lstcontact.size();
    }

    @Override
    public Object getItem(int i) {
        return lstcontact.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context,R.layout.contact_iteam,null);
        ContactItems contact = lstcontact.get(i);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        img.setImageResource(contact.getImageId());
        img.setTag(""+contact.getImageId());

        TextView name = (TextView) v.findViewById(R.id.tvName);
        name.setText(contact.getContactName());

        TextView number = (TextView) v.findViewById(R.id.tvNumber);
        number.setText(contact.getContactNumber());

        TextView email = (TextView) v.findViewById(R.id.tvEmail);
        email.setText(contact.getContactEmail());

        return v;
    }
}
